# #FILE_LAST_MODIFIED#

# DO NOT EDIT: Copyright (c) 2015 Richard T. Bailey rtbaileyphd.com
# DO NOT EDIT: ISC License:
# DO NOT EDIT: Permission to use, copy, modify, and/or distribute this software for any
# DO NOT EDIT: purpose with or without fee is hereby granted, provided that the above
# DO NOT EDIT: copyright notice and this permission notice appear in all copies.
# DO NOT EDIT:
# DO NOT EDIT: THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
# DO NOT EDIT: WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
# DO NOT EDIT: MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
# DO NOT EDIT: ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
# DO NOT EDIT: WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
# DO NOT EDIT: ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
# DO NOT EDIT: OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

##
## Usage:
##  use vars qw(is_dos cmd debug log_file); # and others, if needed
##  require 'file_mgr.pl';

##: file_mgr.pl contains numerous utilities for manipulating files, e.g:
##: qarchive() archives a file (deletes *-3 version, renames *-2 as *-3, etc.).
##: safely_create(*file) archives $file if needed and then opens a new $file.
##: tail() strips the directory name from a file name.
##: extn() returns the extension from a file name.
##: superglob() works better than standard glob and supports metafiles (files that contain lists of globbable file patterns)
##: Numerous file_mgr subroutines assist in writing to main::log_file.
##: The names of most of these subroutines fit the pattern t?[drv][iwe]f? where:
##: t => tee output to STDOUT as well as main::log_file.
##: d => data                   and 'D' inserted in col. 1
##: r => run-time               and 'R' inserted in col. 1
##: v => validation (debugging) and 'V' inserted in col. 1 if $debug
##: i => (normal) information   and 'I' inserted in col. 2
##: w => warning                and 'W' inserted in col. 2
##: e => error                  and 'E' inserted in col. 2
##: f => 1st arg is a printf-style format
##: For example tre() would print run-time error information to STDOUT and to main::log_file.
##: And rif($f, args) is like print log_file "RI: ", sprintf($f,args), "\n"
##: These subroutines also insert ':' in col. 3 and append a newline.
##: tee()  does a print  to both STDOUT and main::log_file.
##: teef() does a printf to both STDOUT and main::log_file.
##: newest() takes a list of files and returns the one newest file.

use POSIX qw(ctime);
use Carp;
use strict;
use File::Copy qw(cp);

$main::is_dos = !defined($ENV{SHELL}) if !defined($main::is_dos); ## true if MS DOS

package file_mgr;

use vars qw($FILE $path_slash $IsInterix);

$path_slash = $main::is_dos ? '\\' : '/';

$IsInterix = defined $ENV{INTERIX_ROOT};

#######################################################################################################################

$file_mgr::nf     = "unnamed_file";
$file_mgr::nftemp = "unnamed_temp_file";

sub main::reset_nf {

    if (@_) {

        $file_mgr::nf = $_[0] ;

    } else {

        $file_mgr::nf = "unnamed_file";

    }

}

#######################################################################################################################

sub main::qarchive {

    $file_mgr::nf = $_[0] if defined($_[0]);

    if (-e $file_mgr::nf || -l $file_mgr::nf) {

        if (-e "$file_mgr::nf-1" || -l "$file_mgr::nf-1") {

            if (-e "$file_mgr::nf-2" || -l "$file_mgr::nf-2") {

                chmod 0777, "$file_mgr::nf-3";
                unlink "$file_mgr::nf-3";

                rename ("$file_mgr::nf-2", "$file_mgr::nf-3");

            }

            rename ("$file_mgr::nf-1", "$file_mgr::nf-2");

        }

        return rename ($file_mgr::nf, "$file_mgr::nf-1");

    }

}

#######################################################################################################################

sub main::archive {

    $file_mgr::nf = $_[0] if defined($_[0]);

    if (-e $file_mgr::nf || -l $file_mgr::nf) {

        &main::tri ("$file_mgr::nf will now be archived.");

        &main::qarchive;

    } else {

        &main::trw ("$file_mgr::nf does not exist, so cannot be archived.");

    }

}

#######################################################################################################################

sub main::safely_create {
## call with a typeglob for which the corresponding scalar has
## been set to the name of the file to create.
## E.g.:
##     $log_file = "logs/xxxx.log";
##     safely_create(*log_file);

    my $nf_glob = $_[0];

    if ($nf_glob !~ m/^\*/) {

        &main::tre (
            "Fatal -- file_mgr safely_create() takes a typeglob arg (e.g., *file_name).\a"
        );

        $^W = 0;

        Carp::confess('');

    }

    $nf_glob =~ s=^.==; ## removes '*'

    my $nf = eval("\$$nf_glob");

    ##print ("nf = $nf\n");

    if ($nf eq '') {

        &main::tre (
            "Fatal -- $nf_glob undefined in call to file_mgr safely_create()\a"
        );

        $^W = 0;

        Carp::confess('');

    }

    $nf = main::NormalizePath($nf); # 01/17/15

    my $old_nf_existed = -e $nf || -l $nf;

    main::qarchive($nf);

    my $cmd = "open ($nf_glob, '> $nf') or die;";

    ##print "Trying $cmd\n";

    eval ($cmd);

    if ($@) {

        &main::tre ("Fatal -- Can't create $nf: $!\a");

        &main::recover() if $old_nf_existed;

        $^W = 0;

        Carp::confess('');

    }

    #print ("Arg2:  $_[1]\n");

    if ($_[1]) {

        #--12/22/11--$cmd = qq(\$file_mgr::old_fh = select($nf_glob); \$| = 1; select(\$file_mgr::old_fh););

        my $old_fh;

        $cmd = qq(\$old_fh = select($nf_glob););

        eval ($cmd);

        $| = 1;

        select($old_fh);

        ##print "Buffering of $nf is off\n";

    }

}

#######################################################################################################################

sub main::qdup  {

    $file_mgr::nf = $_[0] if defined($_[0]);

    if (-e $file_mgr::nf) {

        if (-e "$file_mgr::nf-1" || -l "$file_mgr::nf-1") {

            if (-e "$file_mgr::nf-2" || -l "$file_mgr::nf-2") {

                unlink "$file_mgr::nf-3";

                rename ("$file_mgr::nf-2", "$file_mgr::nf-3");

            }

            rename ("$file_mgr::nf-1", "$file_mgr::nf-2");

        }

        my $result = &main::CopyPreserve("$file_mgr::nf", "$file_mgr::nf-1");

        return $result;

    }

}

#######################################################################################################################

sub main::dup {

    $file_mgr::nf = $_[0] if defined($_[0]);

    if (-e $file_mgr::nf) {

        &main::tri ("$file_mgr::nf will now be duplicated.");

        &main::qdup;

    } elsif (-l $file_mgr::nf) {

        &main::tri ("$file_mgr::nf is a soft link to a nonexistent file, so cannot be duplicated.");

    } else {

        &main::tri ("$file_mgr::nf does not exist, so cannot be duplicated.");

    }

}

#######################################################################################################################

sub main::qrecover  {

    $file_mgr::nf = $_[0] if defined($_[0]);

    return 1 if (! -e "$file_mgr::nf-1" && ! -l "$file_mgr::nf-1");

    if (-e $file_mgr::nf || -l $file_mgr::nf) {

        unlink "$file_mgr::nf+1";

        rename ($file_mgr::nf, "$file_mgr::nf+1");

    }

    rename ("$file_mgr::nf-1", $file_mgr::nf);

    rename ("$file_mgr::nf-2", "$file_mgr::nf-1");

    rename ("$file_mgr::nf-3", "$file_mgr::nf-2");

    return 0;

}

#######################################################################################################################

sub main::recover {

    $file_mgr::nf = $_[0] if defined($_[0]);

    if (-e "$file_mgr::nf-1" || -l "$file_mgr::nf-1") {

        &main::tri ("$file_mgr::nf-1 will now be recovered.");

        &main::qrecover;

    } else {

        &main::trw ("$file_mgr::nf-1 does not exist, so cannot be recovered.");

    }

}

#######################################################################################################################

sub main::qunrecover  {

    $file_mgr::nf = $_[0] if defined($_[0]);

    if (-e "$file_mgr::nf+1" || -l "$file_mgr::nf+1") {

        &main::qarchive;

        return rename("$file_mgr::nf+1", $file_mgr::nf);

    }

}

#######################################################################################################################

sub main::unrecover {

    $file_mgr::nf = $_[0] if defined($_[0]);

    if (-e "$file_mgr::nf+1" || -l "$file_mgr::nf+1") {

        &main::tri ("$file_mgr::nf+1 will now be unrecovered.");

        &main::qunrecover;

    } else {

        &main::trw ("$file_mgr::nf+1 does not exist, so cannot be unrecovered.");

    }

}

#######################################################################################################################

sub main::qswap  {

    $file_mgr::nf = $_[0] if defined($_[0]);

    if (-e "$file_mgr::nf-1" || -l "$file_mgr::nf-1") {

        rename($file_mgr::nf, "$file_mgr::nf.$$");

        rename("$file_mgr::nf-1", $file_mgr::nf);

        rename("$file_mgr::nf.$$", "$file_mgr::nf-1");

    }

}

#######################################################################################################################

sub main::swap {

    $file_mgr::nf = $_[0] if defined($_[0]);

    if (-e "$file_mgr::nf-1" || -l "$file_mgr::nf-1") {

        &main::tri ("$file_mgr::nf-1 will now be swapped with $file_mgr::nf.");

        &main::qswap;

    } else {

        &main::trw ("$file_mgr::nf-1 does not exist, so cannot be swapped with $file_mgr::nf.");

    }

}

#######################################################################################################################

sub main::qappend  {

    $file_mgr::nf      = ($_[0] || 'nf_undefined'      );
    $file_mgr::nftemp  = ($_[1] || 'appendee_undefined');

    if (-e $file_mgr::nftemp && -e $file_mgr::nf) {

        main::qdup($file_mgr::nf);

        open (file_mgr::nf    , ">> $file_mgr::nf") or expire("Cannot append to $file_mgr::nf: $!");
        open (file_mgr::nftemp                    ) or expire("Cannot read $file_mgr::nftemp: $!" );

        while (<file_mgr::nftemp>) {

            print file_mgr::nf;

        }

        close file_mgr::nf    ;
        close file_mgr::nftemp;

    } elsif (-e $file_mgr::nf) {

        &main::tre ("Nonexistent file $file_mgr::nftemp cannot be appended to $file_mgr::nf");

    } elsif (-e $file_mgr::nftemp) {

        &main::tre ("$file_mgr::nftemp cannot be appended to nonexistent file $file_mgr::nf");

    } else {

        &main::tre ("Neither $file_mgr::nf nor $file_mgr::nftemp exist.");

    }

}

#######################################################################################################################

sub main::append {

    $file_mgr::nf      = ($_[0] || 'nf_undefined'      );
    $file_mgr::nftemp  = ($_[1] || 'appendee_undefined');

    &main::tri ("File $file_mgr::nftemp will be appended to $file_mgr::nf");

    main::qappend($file_mgr::nf, $file_mgr::nftemp);

}

#######################################################################################################################

sub main::qtype  {

    $file_mgr::nf = $_[0] if defined($_[0]);

    if (-e $file_mgr::nf) {

        my $hdr = '';

        $hdr = $_[1] if defined($_[1]);

        open (file_mgr::nf);

        while (<file_mgr::nf>) {print "$hdr$_";}

        close (file_mgr::nf);

    }

}

#######################################################################################################################

sub main::type {

    $file_mgr::nf = $_[0] if defined($_[0]);

    if (-e "$file_mgr::nf") {

        printf "%sContents of $file_mgr::nf:\n", (defined($_[1]) ? $_[1] : '');

        &main::qtype(@_);

    } elsif (-l "$file_mgr::nf") {

        print "$file_mgr::nf is a link to a nonexistent file, so cannot be listed.\n";

    } else {

        print "$file_mgr::nf does not exist, so cannot be listed.\n";

    }

}

#######################################################################################################################

sub main::path { # E.g.  path("aa/bb/cc.txt") is "aa/bb"   path("aa") is ""

    local($_) = $_[0];

    if (!m#[/\\]#) {return "";}

    s#[/\\][^/\\]*$##;

    return $_;

}

#######################################################################################################################

sub main::nonblank_path {

    local($_) = $_[0];

    if (!m#[/\\]#) {return '.';}

    s#[/\\][^/\\]*$##;

    return $_;

}

#######################################################################################################################

sub main::path_slash {

    local($_) = $_[0];

    if (!m#[/\\]#) {return "";}

    s#[^/\\]*$##;

    return $_;

}

#######################################################################################################################

sub main::nonblank_path_slash {

    local($_) = $_[0];

    if (!m#[/\\]#) {return './';}

    s#[^/\\]*$##;

    return $_;

}

#######################################################################################################################

sub main::root {  # root("aa/bb/cc.txt") is "aa/bb/cc"

    local($_) = $_[0];

    if (!m#[.]#) {return $_;}

    s#[.][^.]*$##;  ## strip extension

    return $_;

}

#######################################################################################################################

sub main::root_dot {

    local($_) = $_[0];

    if (!m#[.]#) {return $_;}

    s#[.][^.]*$#.#;  ## strip extension except for dot

    return $_;

}

#######################################################################################################################

sub main::base { # base("aa/bb/cc.txt") is "cc"

    local($_) = $_[0];

    s#.*[/\\]##g;   ## strip path

    s#[.][^.]*$##;  ## strip extension

    return $_;

}

#######################################################################################################################

sub main::base_dot {

    local($_) = $_[0];

    s#.*[/\\]##g;    ## strip path

    s#[.][^.]*$#.#;  ## strip extension except for dot

    return $_;

}

#######################################################################################################################

sub main::tail {  # tail("aa/bb/cc.txt") is "cc.txt"

    local($_) = $_[0];

    s#.*[/\\]##g;       ## strip path

    return $_;

}

#######################################################################################################################

sub main::extn {

    local($_) = $_[0];

    if (!m#[.]#) {return "";}

    s#.*[./\\]##g;

    return $_;

}

#######################################################################################################################

@file_mgr::loaded_default_files = ();

sub main::load_defaults {

    ## Evaluates perl assignment statements in a file created by calls to &store_default.
    ## The assignment statements can assign values to scalars, arrays, and hashes.
    ##

    $file_mgr::defaults_file = shift;

    $file_mgr::defaults_file = main::NormalizePath($file_mgr::defaults_file);

    push (@file_mgr::loaded_default_files, $file_mgr::defaults_file);

    #--if (-e $file_mgr::defaults_file) {print("VI: $file_mgr::defaults_file will be loaded\n");}
    #--else                             {print("VI: $file_mgr::defaults_file does not exist\n");}

    return if (!$file_mgr::defaults_file); # 03/08/13 allow an empty defaults file name.

    return if ($file_mgr::defaults_file eq '<none>');

    open (file_mgr::defaults_file);

    return if (!fileno(file_mgr::defaults_file));

    &main::trif ("%s defaults file is $file_mgr::defaults_file", &main::tail($0)) if $main::opt_debug or $main::debug;

    while (<file_mgr::defaults_file>) {

        next if /^#/ || /^\s*$/;

        #--print("VI: eval: $_\n");

        eval;

        print ("RE: Cannot eval this line in $file_mgr::defaults_file :\nRE: '$_'\nRE: reason: $@\n") if $@;

        return if !fileno(file_mgr::defaults_file); # The eval could have resulted in closing the file.  See PerlWizardSubs &switch_defaults_file()

    }

    $main::defaults_file_default = main::NormalizePath($main::defaults_file_default) if defined($main::defaults_file_default);
    $main::log_file_default      = main::NormalizePath($main::log_file_default     ) if defined($main::log_file_default     );

    close file_mgr::defaults_file;

}

#######################################################################################################################

$file_mgr::last_stored_defaults_file = '<none>';
%file_mgr::defaults_files_by_var     = ();

sub main::store_default {

    return if (!$file_mgr::defaults_file); # 03/08/13 allow an empty defaults file name.

    return if ($file_mgr::defaults_file eq '<none>');

    $file_mgr::defaults_file = main::NormalizePath($file_mgr::defaults_file);

    my $var_spec = $_[0];

    # If $var_spec looks like a variable, create assignment statement(s) that can assign defaults.
    # Arrays and hashes are supported if all the elements are scalars.
    # E.g., if $var_spec is '$MyVal', then put this line in the defaults file:
    #       $main::MyVal_default = q(<current value of $MyVal>);
    # If $var_spec looks like something else, just copy @_ verbatim to the defaults file.

    my $defaults_file_orig = '';

    if (defined($file_mgr::defaults_files_by_var{$var_spec})) { # then this is an update, perhaps to a defaults_file opened earlier

        $defaults_file_orig = $file_mgr::defaults_file;

        $file_mgr::defaults_file = $file_mgr::defaults_files_by_var{$var_spec};

    }

    if ($file_mgr::last_stored_defaults_file ne $file_mgr::defaults_file && !$defaults_file_orig) { # then start a new defaults_file

        my $temp = main::path($file_mgr::defaults_file);

        if ($temp && ! -d $temp) {

            mkdir($temp, 0777);

            chmod 0777, $temp;

        } else {

            unlink($file_mgr::defaults_file); # 03/08/13 don't backup the default files.

        }

        while (!open (file_mgr::defaults_file, "> $file_mgr::defaults_file")) {

            last if &main::get_retry_ignore_abort("Can't write to defaults file $file_mgr::defaults_file: $!") eq 'ignore';

        }

        &main::tvif ("Writing new defaults for %s to $file_mgr::defaults_file", &main::tail($0)) if $main::opt_debug || $main::debug;

        $file_mgr::user = defined($main::user) ?
            $main::user :
            ($ENV{NAME} || $ENV{USERNAME} || $ENV{LOGNAME} || $ENV{USER} || getlogin() || (getpwuid($<))[0]);

        printf file_mgr::defaults_file
            "# defaults for $file_mgr::user running $0.  Created %s",
            &POSIX::ctime(time());

        $file_mgr::last_stored_defaults_file = $file_mgr::defaults_file;

    }

    ## print "\$var_with_type = $var_with_type\n";

    #--print("VI: putting $var_spec into $file_mgr::defaults_file\n");

    open (file_mgr::defaults_file, ">> $file_mgr::defaults_file") if !fileno(file_mgr::defaults_file);

    if (!fileno(file_mgr::defaults_file)) {

        &main::trw ("Can't append to defaults file $file_mgr::defaults_file");

        return;

    }

    if ($var_spec =~ /^[\$\@\%][a-z0-9_:]+$/i) {

        my $var_with_type = $var_spec;

        if ($var_with_type !~ /::/) {

            $var_with_type =~ s/^(.)(.*)/$1main::$2/; ## puts main:: ahead of variable name

        }

        push(@file_mgr::vars, $var_with_type . "_default"); ## tracks variables that can be undef'd

        # print assignment statement(s) that can assign the default values using an eval.

        if ($var_with_type =~ /^\$/) { ## then scalar

            eval qq(\$file_mgr::temp = $var_with_type);

            if (defined($file_mgr::temp)) {$file_mgr::temp =~ s=\\=\\\\=g;} ## \ => \\
            else                          {$file_mgr::temp = ''          ;}

            ## print qq($var_with_type has the value "$file_mgr::temp"\n);

            print file_mgr::defaults_file "$var_with_type\_default = q($file_mgr::temp);\n";

        } elsif ($var_with_type =~ /^\@/) { ## regular array

            $file_mgr::var = $var_with_type;

            $file_mgr::var =~ s=^.==;

            print file_mgr::defaults_file qq($var_with_type\_default = ();\n);
            ## clears existing defaults.

            my $index;

            eval qq(\$file_mgr::element_ct = scalar $var_with_type);

            for ($index = 0; $index < $file_mgr::element_ct; $index++) {

                eval qq(\$file_mgr::temp = \$$file_mgr::var\[$index]);

                $file_mgr::temp =~ s=\\=\\\\=g; ## \ => \\

                $index = " $index" if $index < 10;

                print file_mgr::defaults_file qq(\$$file_mgr::var\_default[$index] = q($file_mgr::temp);\n);

            }

        } elsif ($var_with_type =~ /^\%/) { ## associative array

            $file_mgr::var = $var_with_type;

            $file_mgr::var =~ s=^.==;

            print file_mgr::defaults_file qq($var_with_type\_default = ();\n);
            ## clears existing defaults.

            eval qq(\@file_mgr::var_keys = keys $var_with_type);

            foreach $file_mgr::key (@file_mgr::var_keys) {

                eval qq(\$file_mgr::temp = \$$file_mgr::var\{$file_mgr::key});
                ## note \ preceding {

                $file_mgr::temp =~ s=\\=\\\\=g; ## \ => \\

                print file_mgr::defaults_file qq(\$$file_mgr::var\_default{$file_mgr::key} = q($file_mgr::temp);\n);

            }

            undef(@file_mgr::var_keys);

        }

        $file_mgr::defaults_files_by_var{$var_spec} = $file_mgr::defaults_file; # see sub main::update_default

    } else { # just copy the string to the file

        print file_mgr::defaults_file qq(@_\n);

    }

    close file_mgr::defaults_file;

    #main::type($file_mgr::defaults_file, "VI: after $var_spec: ");

    $file_mgr::defaults_file = $defaults_file_orig if $defaults_file_orig;

}

#######################################################################################################################

sub main::update_default { # use this if there are multiple defaults_files and switching back to an earlier one is needed

    $file_mgr::defaults_file = $file_mgr::defaults_files_by_var{$_[0]} if defined($file_mgr::defaults_files_by_var{$_[0]});

    main::store_default($_[0], 'append');

}

#######################################################################################################################

sub main::undef_defaults {

    my $var;

    foreach $var (@file_mgr::vars) {

        eval "undef $var";

    }

    undef @file_mgr::vars;

}

#######################################################################################################################

sub main::print_stats {

    my $file;

    my @stats;

    my $mtime;

    foreach $file (@_) {

        @stats = lstat($file);

        #   0 dev      device number of filesystem
        #   1 ino      inode number
        #   2 mode     file mode  (type and permissions)
        #   3 nlink    number of (hard) links to the file
        #   4 uid      numeric user ID of file's owner
        #   5 gid      numeric group ID of file's owner
        #   6 rdev     the device identifier (special files only, iff unix (non zero in Windows))
        #   7 size     total size of file, in bytes
        #   8 atime    last access time in seconds since the epoch
        #   9 mtime    last modify time in seconds since the epoch
        #  10 ctime    inode change time in seconds since the epoch (*)
        #  11 blksize  preferred block size for file system I/O
        #  12 blocks   actual number of blocks allocated

        if (defined($stats[9])) {

            $mtime = localtime($stats[9]);

            &main::trif (
                "%s %9d bytes %s %s",
                $mtime,
                $stats[7],
                (-d _ ? 'dir ' : -l _ ? 'link' : -f _ ? 'file' : '???'),
                $file
            );

        } elsif (! -e $file) {

            &main::trw  ("File '$file' not found by print_stats()");

        } else {

            &main::trw  ("File '$file' was found but Perl stat() failed in print_stats().");

        }

    }

}

#######################################################################################################################

sub main::tee  {print "@_\n"; print main::log_file "@_\n" if fileno(main::log_file)} ## tee
sub main::teef {my $fmt = shift; main::tee sprintf $fmt, @_}                 ## tee formatted

sub main::ri {print main::log_file "RI: @_\n" if fileno(main::log_file)} ## log run-time   info
sub main::rw {print main::log_file "RW: @_\n" if fileno(main::log_file)} ## log run-time   warning
sub main::re {print main::log_file "RE: @_\n" if fileno(main::log_file)} ## log run-time   error
sub main::di {print main::log_file "DI: @_\n" if fileno(main::log_file)} ## log data       info
sub main::dw {print main::log_file "DW: @_\n" if fileno(main::log_file)} ## log data       warning
sub main::de {print main::log_file "DE: @_\n" if fileno(main::log_file)} ## log data       error
sub main::vi {return if !$main::debug; print main::log_file "VI: @_\n" if fileno(main::log_file)} ## log validation info
sub main::vw {return if !$main::debug; print main::log_file "VW: @_\n" if fileno(main::log_file)} ## log validation warning
sub main::ve {return if !$main::debug; print main::log_file "VE: @_\n" if fileno(main::log_file)} ## log validation error

sub main::rif {my $fmt = shift; main::ri sprintf $fmt, @_} ## log run-time   info    formatted
sub main::rwf {my $fmt = shift; main::rw sprintf $fmt, @_} ## log run-time   warning formatted
sub main::ref {my $fmt = shift; main::re sprintf $fmt, @_} ## log run-time   error   formatted
sub main::dif {my $fmt = shift; main::di sprintf $fmt, @_} ## log data       info    formatted
sub main::dwf {my $fmt = shift; main::dw sprintf $fmt, @_} ## log data       warning formatted
sub main::def {my $fmt = shift; main::de sprintf $fmt, @_} ## log data       error   formatted
sub main::vif {return if !$main::debug; my $fmt = shift; main::vi sprintf $fmt, @_} ## log validation info    formatted
sub main::vwf {return if !$main::debug; my $fmt = shift; main::vw sprintf $fmt, @_} ## log validation warning formatted
sub main::vef {return if !$main::debug; my $fmt = shift; main::ve sprintf $fmt, @_} ## log validation error   formatted

sub main::tri {print "RI: @_\n"; print main::log_file "RI: @_\n" if fileno(main::log_file)} ## tee log run-time   info
sub main::trw {print "RW: @_\n"; print main::log_file "RW: @_\n" if fileno(main::log_file)} ## tee log run-time   warning
sub main::tre {print "RE: @_\n"; print main::log_file "RE: @_\n" if fileno(main::log_file)} ## tee log run-time   error
sub main::tdi {print "DI: @_\n"; print main::log_file "DI: @_\n" if fileno(main::log_file)} ## tee log data       info
sub main::tdw {print "DW: @_\n"; print main::log_file "DW: @_\n" if fileno(main::log_file)} ## tee log data       warning
sub main::tde {print "DE: @_\n"; print main::log_file "DE: @_\n" if fileno(main::log_file)} ## tee log data       error
sub main::tvi {return if !$main::debug; print "VI: @_\n"; print main::log_file "VI: @_\n" if fileno(main::log_file)} ## tee log validation info
sub main::tvw {return if !$main::debug; print "VW: @_\n"; print main::log_file "VW: @_\n" if fileno(main::log_file)} ## tee log validation warning
sub main::tve {return if !$main::debug; print "VE: @_\n"; print main::log_file "VE: @_\n" if fileno(main::log_file)} ## tee log validation error

sub main::trif {my $fmt = shift; main::tri sprintf $fmt, @_} ## tee log run-time   info    formatted
sub main::trwf {my $fmt = shift; main::trw sprintf $fmt, @_} ## tee log run-time   warning formatted
sub main::tref {my $fmt = shift; main::tre sprintf $fmt, @_} ## tee log run-time   error   formatted
sub main::tdif {my $fmt = shift; main::tdi sprintf $fmt, @_} ## tee log data       info    formatted
sub main::tdwf {my $fmt = shift; main::tdw sprintf $fmt, @_} ## tee log data       warning formatted
sub main::tdef {my $fmt = shift; main::tde sprintf $fmt, @_} ## tee log data       error   formatted
sub main::tvif {return if !$main::debug; my $fmt = shift; main::tvi sprintf $fmt, @_} ## tee log validation info    formatted
sub main::tvwf {return if !$main::debug; my $fmt = shift; main::tvw sprintf $fmt, @_} ## tee log validation warning formatted
sub main::tvef {return if !$main::debug; my $fmt = shift; main::tve sprintf $fmt, @_} ## tee log validation error   formatted

#######################################################################################################################

sub main::superglob {
## is like glob except:
## (1) a spec of the form "@filename" causes superglob() to open filename
##     to look for more specs (i.e., the spec can include metafiles).
##     Metafiles can include other metafile specs, but any metafile specs named
##     inside metafiles must be reachable from the original working directory.
## (2) glob() with multiple args can result in multiple hits for the same file.
##     superglob() eliminates duplicates.
## (3) superglob allows for spaces in file name candidates that do not have wild characters.
##     E.g., superglob("abc xyz.eee") would look for the single file 'abc xyz.ee', but
##     suerglob("abc xyz.*") would look for 'abc' and all files matching xyz.*
##     bsd_glob allows for names with spaces, but bsd_glob is not universally available.
##
## superglob() is case insensitive under MS-DOS
##

    my $spec;
    my %metafiles;
    my @expanded_specs; ## array used to preserve order
    my %expanded_specs; ## hash used to avoid duplicates
    my @specs = @_;
    my @invalid_metafiles = ();

    while (@specs) {  # process metafiles, if any

        $spec = shift(@specs);

        if ($spec =~ m=^[@](.*)$=) {

            $file_mgr::metafile = $1; # $spec with @ prefix removed

        } else {

            $file_mgr::metafile = 0;

        }

        ##printf "VI: Processing spec $spec\n";

        ##print "VI: \$file_mgr::metafile = $file_mgr::metafile\n" if $file_mgr::metafile ;

        if ($file_mgr::metafile) {

            if (-e $file_mgr::metafile) {

                if (!defined($metafiles{$file_mgr::metafile})) {
                ## then this is the 1st occurence of $file_mgr::metafile

                    open (file_mgr::metafile);

                    #print("VI: Opened $file_mgr::metafile\n");

                    while (<file_mgr::metafile>) {

                        s=#.*$==; ## strip comments

                        next if ! /\S/; ## skip white lines

                        s/\s*$//; # remove \n and \r from EOL

                        push(@specs, $_); ## @specs intended.  No splitting.

                        ##printf ("\@specs = @specs (%d total)\n", scalar(@specs));

                    }

                    $metafiles{$file_mgr::metafile} = 1;

                }

            } else {

                main::tre("Cannot find metafile $file_mgr::metafile\a");

                push(@invalid_metafiles, $spec); ## intended to propagate error

            }

        } elsif (!defined($expanded_specs{$spec})) {

            $expanded_specs{$spec} = 1; ## needed to avoid duplicates

            push(@expanded_specs, $spec);

        }

    }  # endloop to process metafiles

    ## printf ("expanded_specs = @expanded_specs\n");

    my $file;

    my @files = ();

    my %files_seen = ();

    foreach $spec (@expanded_specs) {  # process real file specs that Perl's glob can handle

        foreach $file ($spec =~ /[*?\[{]/ ? glob($spec) : ($spec)) {

            next if $files_seen{$file}; # eliminate duplicates

            push(@files, $file);

            $files_seen{$file} = 1;

        }

    } # endloop to process real file specs

    push(@files, @invalid_metafiles, ); ## intended to propagate error

    ## printf ("returning  %s\n", join('  ', @files));

    return @files;

}

#######################################################################################################################

sub main::newest { # return the newest file from a list of files.  Ties go to the last in the list.

    my $file;

    my $mtime;

    my $newest_time = 0;

    my $newest_file = '';

    ## print "Searching for newest of @_\n";

    foreach $file (main::superglob(@_)) {

        $mtime = (stat $file)[9];

        ## print "No stats on $file\n" if !defined($mtime);

        next if !defined($mtime);

        ## print "\$mtime of $file is $mtime\n";

        next if $mtime < $newest_time;

        $newest_time = $mtime;

        $newest_file = $file;

    }

    ## print "Newest of @_ is $newest_file\n";

    ## main::tre("file_mgr::newest() found no existing files in: @_");

    return $newest_file;

}

#######################################################################################################################

# This is like mkdir -p available on some Unix systems.  An array of directory trees to be made can be passed in.
sub main::mkdir_tree {

    while ($_ = shift) {

        my $subdir;
        my $dir = '';

        for $subdir (split(m=/|\\=, $_)) {

            $dir .= "$subdir/";

            #--main::tri("Checking \"$dir\"");

            if (! -e $dir) {

                #--main::tri("Making dir = $dir");

                mkdir $dir, 0777;

                chmod 0777, $dir;

                if (! -e $dir) {

                    main::tre("Unable to create dir $dir: $!");

                    return 0;

                }

            }

        }

    }

    1;

}

#######################################################################################################################

sub main::DirsAllExist {

    return 0 if !@_;

    my $Dir;

    foreach $Dir (@_) {

        return 0 if !$Dir;

        $Dir = main::NormalizePath($Dir);

        #print "$Dir does not exist\n" if ! -d $Dir;

        return 0 if ! -d $Dir;

    }

    return 1;

}

#######################################################################################################################

sub main::FilesAllExist { # including dirs

    return 0 if !@_;

    my $File;

    foreach $File (@_) {

        return 0 if !$File;

        $File = main::NormalizePath($File);

        return 0 if ! -e $File;

    }

    return 1;

}

#######################################################################################################################

sub main::PlainFilesAllExist { # excluding dirs

    return 0 if !@_;

    my $File;

    foreach $File (@_) {

        return 0 if !$File;

        $File = main::NormalizePath($File);

        return 0 if ! -e $File || -d _;

    }

    return 1;

}

#######################################################################################################################

$file_mgr::NormalizePathCallCt = 0;

sub main::NormalizePath {  # Arg 0: file name to convert.  Arg 2: optional effective $is_dos

    my  $UnixOrWindowsPathName = shift;
    my  $is_dos = main::is_true(shift || $main::is_dos);  # So 2nd arg 'false' can get Unix names from DOS
    my  @PathParts;
    my  $PathPart = '';
    my  $PathPartsIndex;
    my  $OutputPathName = '';
    my  $OutputPathNameNext;

    #printf("VI: NormalizePath processing $UnixOrWindowsPathName.  \$is_dos = $is_dos\n");

    return '' if ! $UnixOrWindowsPathName; # not an error

    if ($is_dos) {

        $file_mgr::NormalizePathCallCt++;

        #PathParts.GetFromStr(UnixOrWindowsPathName, "/\\");
        @PathParts = split(m=/|\\=, $UnixOrWindowsPathName);

        shift @PathParts if ! $PathParts[0];

        #printf("VI: '$UnixOrWindowsPathName' %d \@PathParts = '@PathParts'\n", scalar(@PathParts));

        #printf("VI: \@PathParts = '%s' '%s' ... \n", $PathParts[0], $PathParts[1]);

        if ($UnixOrWindowsPathName =~ m=^/dev/fs/=) { # Assumes DOS<->Unix mapping like  C:\aaa <-> /dev/fs/C/aaa

            $PathPartsIndex = 2;

            $PathParts[2] .= ":";

            #printf("VI: Interix OutputPathName = $OutputPathName\n");

        } elsif ($UnixOrWindowsPathName =~ m=^/[A-Z]/=) { # Assumes DOS<->Unix mapping like  C:\aaa <-> /C/aaa

            $PathPartsIndex = 0;

            $PathParts[0] .= ":";

            #printf("VI: Interix OutputPathName = $OutputPathName\n");

        } else {

            $PathPartsIndex = 0;

        }

        while (1) {

            $PathPart = $PathParts[$PathPartsIndex];

            #printf("VI: PathPart = $PathPart\n");

            $OutputPathNameNext = $OutputPathName . $PathPart;

            $file_mgr::nf = $OutputPathNameNext;

            #--not needed anymore, plus causes unlink() to fail in find--if (open(file_mgr::nf)) { # to accommodate Interix soft link
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--    #char                                        Interix[9] = "";
            #--not needed anymore, plus causes unlink() to fail in find--    my                          $Interix;
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--    if (sysread (file_mgr::nf, $Interix , 8) && $Interix eq "IntxLNK\001") { # then it is an Interix soft link
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--        #char                                        NextChr;
            #--not needed anymore, plus causes unlink() to fail in find--        my                          $NextChr;
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--        $OutputPathNameNext = $OutputPathName;
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--        # Note:  this code only handles relative links
            #--not needed anymore, plus causes unlink() to fail in find--        while (sysread (file_mgr::nf, $NextChr, 1)) {
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--            $OutputPathNameNext .= ($NextChr eq '/' ? '\\' : $NextChr);
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--            sysread (file_mgr::nf, $NextChr, 1); # skip the Unicode extended part of the character
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--        }
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--        close(file_mgr::nf);
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--        if ($file_mgr::NormalizePathCallCt < 100) {$OutputPathNameNext = main::NormalizePath($OutputPathNameNext);} # In case of multiple links.
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--    } else {
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--        close($file_mgr::nf);
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--    }
            #--not needed anymore, plus causes unlink() to fail in find--
            #--not needed anymore, plus causes unlink() to fail in find--}

            $OutputPathName = $OutputPathNameNext;

            last if (++$PathPartsIndex > $#PathParts);

            $OutputPathName .= "\\";

        }

        $OutputPathName .= "\\" if ($OutputPathName =~ /^.:$/ || $UnixOrWindowsPathName =~ m=[/\\]$=); # 05/21/16 trailing backslash needed for "C:" or "xxx/yy/".

        $UnixOrWindowsPathName = $OutputPathName;

        $file_mgr::NormalizePathCallCt--;

    } else { # running under Unix or Interix

        #print("Name conversion: $UnixOrWindowsPathName -> ");

        $UnixOrWindowsPathName =~ s=\\=/=g;

        if ($UnixOrWindowsPathName =~ m=^([a-z]):=i) { # Found Drive letter, so that must be converted.

            # The assumption here is that C: is mapped to /C, D: to /D, etc.

            substr($UnixOrWindowsPathName, 0, 1) = uc($1); # convert to upper case drive

            #$UnixOrWindowsPathName =~ s=^([A-Z]):=/dev/fs/$1=;  # This could be used for Interix

            $UnixOrWindowsPathName =~ s=^([A-Z]):=/$1=; # 09/09/14 use the mapping C:\aa\bb -> /C/aa/bb

        }

        #print("$UnixOrWindowsPathName\n");

    }

    #printf("VI: NormalizePath returning  $UnixOrWindowsPathName\n");

    return $UnixOrWindowsPathName;

}

#######################################################################################################################

sub main::NormalizePaths {

    my @NormalizedPaths = ();

    foreach (@_) {

        push(@NormalizedPaths, main::NormalizePath($_));

    }

    return @NormalizedPaths;

}

#######################################################################################################################

sub main::type_log { # lists a file into main::log_file

    close(main::log_file);

    printf ("%s\n", '=' x 100);

    main::type($main::log_file);

    if (!open(main::log_file, ">>$main::log_file")) {

        print("RE: Could not reopen $main::log_file for appending after typing it\n");

    }

    printf ("%s\n", '=' x 100);

}

#######################################################################################################################

sub main::FixDriveLetter { # make certain it is a capital letter

    my $FileName = shift;

    return $FileName if ! $main::is_dos || ! ($FileName =~ /^([a-z])(:.*)/);

    return uc($1) . $2;

}

#######################################################################################################################

sub main::SystemDrives { # Windows only

    my @Result;

    for ('A' .. 'Z') {

        $_ .= ':';

        push(@Result, $_) if -d $_;

    }

    return @Result;

}

#######################################################################################################################

sub main::WriteableSystemDrives { # Windows only

    my @Result;

    for ('A' .. 'Z') {

        $_ .= ':';

        push(@Result, $_) if -d $_ && `fsutil fsinfo drivetype $_` !~ /CD-ROM/;

    }

    return @Result;

}

#######################################################################################################################

sub main::SubDirs { # Finds immediate subdirectories of $_[0] even if $_[0] has a space in it or $_[0] eq 'C:'

    my $Dir = shift;

    # glob("$Dir/*") will not work if $Dir has spaces in it and chdir($Dir) will not work if $Dir is just a DOS drive
    # (e.g., C: or C:\), so use the glob approach if $Dir has no spaces and the chdir approach otherwise.

    return () if ! -d $Dir;

    my @AllFiles;

    my @Result = ();

    if ($Dir =~ m/\s/) {

        chdir $Dir;

        @AllFiles = glob('*'); # This is OK even if a file has spaces in it.

        for (@AllFiles) {push(@Result, $_) if -d $_;}

        # NOTE: $main::cwd must be current.
        chdir $main::cwd;

    } else {

        @AllFiles = glob("$Dir/*");

        for (@AllFiles) {push(@Result, main::tail($_)) if -d $_;}

    }

    return @Result;

}

#######################################################################################################################

sub main::CopyPreserve  { # like Unix cp -p, which preserves timestamp

    if (-e $_[0]) {

        my $atime = (stat _)[8];
        my $mtime = (stat _)[9];

        if (File::Copy::cp($_[0], $_[1])) {

            utime ($atime, $mtime, $_[1]);

            return 1;

        } else {

            main::tre(qq(File "$_[0]" cannot be copied to "$_[1]: $!"));

        }

    } else {

        main::tre(qq(Nonexistent file "$_[0]" cannot be copied to "$_[1]"));

    }

    return 0;

}

#######################################################################################################################

sub main::CopyIfNewer  { # copies src to target only if src is newer than target

    return CopyPreserveIfNewer(@_, 1);

}

#######################################################################################################################

sub main::CopyPreserveIfNewer  { # only does the copy if src is newer than target and (optionally) preserves timestamp in copy

    # Timestamp preservation is omitted if $_[2] is true

    if (-e $_[0]) {

        my $atimesrc = (stat _)[8];
        my $mtimesrc = (stat _)[9];
        my $atimetgt;
        my $mtimetgt;

        if (-e $_[1]) {

            $atimetgt = (stat _)[8];
            $mtimetgt = (stat _)[9];

        } else {

            $atimetgt = $mtimetgt = 0;

        }

        if ($mtimesrc <= $mtimetgt) {

            #main::tri(qq("$_[0]" $mtimesrc not newer than "$_[1]" $mtimetgt));

            return -1;

        }

        if (File::Copy::cp($_[0], $_[1])) {

            utime ($atimesrc, $mtimesrc, $_[1]) unless $_[2];

            return 1;

        } else {

            main::tre(qq(File "$_[0]" cannot be copied to "$_[1]: $!"));

        }

    } else {

        main::tre(qq(Nonexistent file "$_[0]" cannot be copied to "$_[1]"));

    }

    return 0;

}

#######################################################################################################################

sub main::print_log_file_hint {

    my $temp = main::NormalizePath($main::log_file);
    my $cat  = $main::is_dos ? 'type' : 'cat';

    print "To view log            :  $cat $temp\n";
    print "To see options used    :  $cat $temp | perl -ne \"print if /^DI: option/\"\n";
    print "To see errors (if any) :  $cat $temp | perl -ne \"print if /^.E:/\"\n";

}

#######################################################################################################################

sub main::grep_file {

    my $file  = $_[0];
    my $regex = $_[1] || '.';
    my $hdr   = $_[2] || '';

    open FILE, "<$file";

    while (<FILE>) { # do not use foreach here even though it works

        print $_ if $_ =~ /$regex/;

    }

    close FILE;

}

#######################################################################################################################

sub main::CommonDir { # Arg is an array of path names.  The dir common to all of them is returned.

    # CommonDir() handles mixed case names correctly whether or not $main::is_dos is true.

    my @NormalizedPaths = main::NormalizePaths(@_);

    #main::tri("CommonDir NormalizedPaths: @NormalizedPaths");

    my  $ItemCt = 0;
    my  @PathParts;

    foreach (@NormalizedPaths) {

        #-NO: $_ =~ s{/dev/fs}{} if ($IsInterix);   # /dev/fs is not a useful part of the name.

        #print("VI: \$_ = $_ in CommonDir()\n");

        @{$PathParts[$ItemCt]} = (split(m{[/\\]}, $_) , "$ItemCt"); # add a unique element to the end in case all $_ are the same

        $ItemCt++;

    }

    #RefTee(\@PathParts, "VI: PathParts: ");

    return '' if !$ItemCt;
    return $_[0] if $ItemCt == 1;

    my  @Result;
    my  $ItemIndex;
    my  @CommonParts;
    my  $AllPartsEqual = 1;
    my  $PartIndex;

    for ($PartIndex = 0; $AllPartsEqual; $PartIndex++) {

        my $RefPart = ${$PathParts[0]}[$PartIndex];

        for ($ItemIndex = 1; $ItemIndex < $ItemCt; $ItemIndex++) {


            $AllPartsEqual =
                $main::is_dos ? lc(${$PathParts[$ItemIndex]}[$PartIndex]) eq lc($RefPart) :
                                   ${$PathParts[$ItemIndex]}[$PartIndex]  eq    $RefPart  ;

            last if ! $AllPartsEqual;

        }

        last if ! $AllPartsEqual;

        push (@CommonParts, $RefPart);

        #RefTee(\@CommonParts, "VI: Common Parts: ");

    }

    return join($path_slash, @CommonParts);

}

#######################################################################################################################

sub main::FlattenPath  { # e.g. "C:\aa aa\bb\cc\dd" -> C^aa_aa^bb^cc\dd now matter what dd is

    my $Tail = main::tail($_[0]);
    my $Path = main::path($_[0]);

    $Path =~ s{/dev/fs/}{} if ($IsInterix);   # /dev/fs/ is not a useful part of the name.
    $Path =~ s{^[/\\]}{};                     # remove leading / or \
    $Path =~ s{[/\\:]+}{^}g;                  # replace strings of / or : or \ with one ^.
    $Path =~ s{ }{_}g;                        # replace blanks with _ since I do not like blanks in file names.

    my $Result = $Path . $path_slash . $Tail; # blanks in $Tail are OK

    #main::tri("FlattenPath() returning $Result");

    return $Result;

}

#######################################################################################################################

sub get_path_slash  {$path_slash;}

#######################################################################################################################

sub main::CutCommonDir  { # Remove CommonDir(@_) from $_[0] and return the result

    $_[0] = main::NormalizePath($_[0]);
    $_[1] = main::NormalizePath($_[1]);

    #main::tri("CutCommonDir normalized args:  '$_[0]'  '$_[1]'");

    my $CommonDir = quotemeta(main::CommonDir(@_));

    #main::tri("CommonDir:  '$CommonDir'");

    my $Result = $_[0];

    $Result =~ s/^$CommonDir//;

    #main::tri("CutCommonDir returning $Result");

    return $Result;

}

#######################################################################################################################

sub main::FileContents {
    $FILE     = $_[0];
    open FILE, "<$FILE";
    binmode FILE;
    my $Contents = do { local( $/ ) ; <FILE> }; # Slurp the whole FILE into a single string
    close FILE;
    return $Contents;
}

#######################################################################################################################

#--sub main::  {



#--}

#######################################################################################################################

#--sub main::  {



#--}

#######################################################################################################################

1; # <--------
