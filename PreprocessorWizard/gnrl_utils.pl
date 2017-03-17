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

## Usage:
##  require 'file_mgr.pl';
##  require 'gnrl_utils.pl';

##: gnrl_utils contains general-purpose utilities, e.g.:
##: get_stdin()      is a subroutine to get user input from STDIN.
##:                  The args are:
##:                  $prompt      This should be obvious, except that get_stdin()
##:                               will wrap \n around it.
##:                  $default     default value, which will be used if user merely
##:                               types carriage return.
##:                  $regexp      A regular express that the user's input must match,
##:                               except this can be 'bool', in which case
##:                               get_stdin() uses '/^[YNTFyntf01]/' for $regexp and
##:                               returns 0 if answer =~ /^[NFnf0]/.
##:                  $hint        Instructions to type if user's entry does not match
##:                               $regexp.
##:                  args are optional.  get_stdin() supplies defaults where required.
##:                  Example:  $stuff = get_stdin(
##:                                'Enter stuff',
##:                                $stuff_default,
##:                                '/^\w+$/',
##:                                'I meant alphanumeric stuff'
##:                            );
##:                  Example:  get_stdin('Enter anything'); ## result will be in $main::stdin
##:                  Example:  $DoIt = get_stdin('Do such-and-such? (y/n)', 'y', 'bool');
##: MkMenu()         Computes list-style $regexp for get_stdin().  Example:
##:                  $Action = get_stdin("What do you want to do?", 'Execute', MkMenu(qw(Execute Restart Abort))); # user sees a menu selection
##: expire()         is like die() , except it pauses if run from MS Windows.
##: quit()           is like exit(), except it pauses if run from MS Windows.
##: do_log_cmd()     executes $main::cmd, logs effort, checks for error
##: printenv()       prints shell and Perl environment.
##: csv_split()      splits comma-separated-value strings.
##: signature()      computes 32-bit signature (hash) of @_
##: FormattedTime()  returns a time string in any imaginable format.  Google "man strftime".
##: HHMMSS()         returns hh:mm:ss time of current or specified time
##: MMDDYYHHMMSS()   returns mm/dd/yy hh:mm:ss time of current or specified time
##: RefPrint()       Outputs a nicely formatted representation of complex objects (e.g., hashses or arrays
##:                  containing scalars and arrays and hashes that contain other arrays, hashes, and scalars, etc.

use POSIX qw(mktime);

use Carp;

use strict;

package gnrl_utils;

use vars qw(
    $get_stdin_ct
);

$get_stdin_ct = 0;

####################################################################################################

sub do_if_found {

    my $do_file;

    my $full_do_file;

    my $found_it;

    my $dir;

    foreach $do_file (@_) {

        $found_it = 0;

        tvi("--------------\n    Searching for $do_file");

        foreach $dir (@INC) {

            $full_do_file = "$dir/$do_file";

            tvi("Checking for $full_do_file");

            next if ! -e $full_do_file;

            $found_it = 1;

            ri("Doing $full_do_file");

            do $full_do_file;

            last;

        }

        ri("There is no $do_file") if ! $found_it; ## Not an error or a warning

    }

}

$main::is_dos = !defined($ENV{SHELL}) if !defined($main::is_dos); ## true if MS DOS

####################################################################################################

sub main::PauseToView { # 04/12/13 prevents programs started by shortcuts from disappearing when something should be seen by the user

    # If you set PROMPT in Advanced System Setting / Environment Variables, this will break:
    return ($main::is_dos && main::is_false($ENV{PROMPT})) || main::is_true($ENV{PauseToView}) || main::is_true($ENV{PAUSEONERR});

}

####################################################################################################

sub main::expire { ## Pause if run from MS Windows.  Should be preceded by explanatory error message.

    Carp::cluck(@_ ? @_ : "Stopped\a"); # print a traceback

    if (main::PauseToView()) {

        print 'Check the error message above.  Press enter to exit.  '; <STDIN>;

    }

    exit -13;

}

####################################################################################################

sub main::quit { ## Pause if run from MS Windows shortcut, double-click, or scheduledjob

    if (main::PauseToView()) {

        print "$0 is done. Press enter to continue.  "; <STDIN>;

    }

    exit defined($_[0]) ? $_[0] : 0;

}

####################################################################################################

$gnrl_utils::DefeatAutoMenuingDelimiters = '!=_&'; # Use one of these delimiters if get_stdin() is creating a menu you don't want.
$gnrl_utils::AutoMenuingDelimiters       = '/#%~:@PQRSTUVWXYZ'; # Possible delimiters not in $DefeatAutoMenuingDelimiters for use in main::MkMenu()

sub main::menu {
## returns a list if regular expression ($_[0]) appears to be a list of choices and delimiter is not in $DefeatAutoMenuingDelimiters
## E.g: if $_[0] = '/^(Sunday|Monday)$/i' then the return value is ('Sunday', 'Monday').
## Suitable menuable expressions can be created by MkMenu() and qw() like this:    MkMenu(qw(Jan Feb Mar ...))
## menu() will not work right if any item contains an escaped bar.

    # print("VI: Checking '$_[0]'\n");

    my @menu;

    if ($_[0] =~ m=^([^$gnrl_utils::DefeatAutoMenuingDelimiters])\^\(([^|]*([|][^|]*)+)\)\$\1i?$=) {

        # $1 is the delimiter
        # $2 is the heart of the menu.  E.g., the join() in MkMenu

        my $temp = $2;

        $temp =~ s=\\(.)=$1=g;

        @menu = split('[|]', $temp);

    }

    return @menu;

}

####################################################################################################

sub main::MkMenu { # 04/09/13 Creates a menuable expression from an array of choices - see main::menu above

    # For example:   MkMenu(qw(Sun Mon Tue Wed Thu Fri Sat))

    my @Choices;

    for (@_) {push(@Choices, quotemeta($_));}

    my $Delim; # A character that is not in "@Choices"

    foreach $Delim (split('', $gnrl_utils::AutoMenuingDelimiters)) { # Possible delimiters

        if ("@Choices" !~ m/[$Delim]/) {

            return $Delim . '^(' . join('|', @Choices) . ')$' . $Delim . 'i';

        }

    }

    tref("FATAL - Can't find delimiter for '@Choices' at %s:%d", __FILE__, __LINE__);

    expire();

}

####################################################################################################

$main::null_str = "''" if !defined($main::null_str);

sub main::get_stdin {

    my ($prompt, $stdin_default, $regexp, $hint, $quiet, $ignore) = @_;

    $prompt      = 'Enter value' if main::is_false($prompt);
    $regexp      = '' if !defined($regexp);
    my $bool     = $regexp eq 'bool';

    $stdin_default = '' if !defined $stdin_default;

    if ($bool) {

        $regexp = '/^yntf01[eora]?/i' if !$regexp;

        if (main::is_false($stdin_default)) {

            $stdin_default = 'no/false'; # looks better than '0' or ''

        }

    } elsif ($regexp =~ /^(efile|edir|epgm|efod|!efod)[?]?$/) {

        ; # do nothing

    } elsif ($regexp ne '' && !eval("\$stdin_default =~ m$regexp")) {

        if ($stdin_default) {

            print("DW: The default value '$stdin_default' is invalid, so cannot be used.\n");
            print("DW: In particular, '$stdin_default' !~ m$regexp\n");

        }

        # If your default values are getting inexplicably wiped out, it could be here.
        # Either the default is invalid or the $regexp is invalid.
        $stdin_default = ''; # clear the default if it fails the regexp test.

    }

    $get_stdin_ct++;

    my @menu = ();

    main::get_hint(\$regexp, \$hint, \@menu);

    if (main::is_false($main::batch)) {

        printf("VI: \$regexp = $regexp  \$hint = '$hint'  menu has %d items\n", scalar @menu) if $main::debug;

        if (@menu) { # then $regexp looks like a list of choices

            my $i;

            my $default_i = 0;

            for ($i = 0; $i < @menu; $i++) {

                my $temp = quotemeta($menu[$i]);

                $default_i = $i + 1 if $stdin_default && $stdin_default =~ m=^$temp$=i;

                #print qq(After checking if "$stdin_default" =~ m=^$menu[$i]\$=i  \$default_i = $default_i\n);

                $menu[$i] = '<none>' if $menu[$i] eq '';

            }

            $stdin_default = '' if ! $default_i;

            # Section: menu input

            while (1) {

                print "\n\n$prompt\n\n";

                for ($i = 0; $i < @menu; $i++) {

                    printf "%2d    $menu[$i]\n", $i + 1;

                }

                printf
                    "\nPlease enter one of the following:\n" .
                    "*  just press enter to accept the default.\n" .
                    "*  a number from 1 to %d\n" .
                    "*  the full text of a choice\n" .
                    "*  l to choose last item\n" .
                    "*  qq to stop program\n\n",
                    scalar(@menu);

                if ($stdin_default ne '') {

                    printf "(default is $default_i ($stdin_default)):  ";

                } else {

                    print "(no default):  ";

                }

                $gnrl_utils::LastMenuIndex = -1;

                chop($main::stdin = <STDIN>);

                if ($main::stdin eq '') {

                    $main::stdin = $stdin_default;

                    $gnrl_utils::LastMenuIndex = $default_i - 1;

                } elsif ($main::stdin eq $main::null_str) {

                    $main::stdin = ''; # $gnrl_utils::LastMenuIndex remains -1

                } elsif ($main::stdin =~ m=^\s*(\d+)\s*$= && $1 > 0 && $1 <= @menu) { # then user chose by item number

                    $main::stdin = $menu[$1 - 1];

                    $gnrl_utils::LastMenuIndex = $1 - 1;

                } elsif ($main::stdin eq 'qq') {

                    main::tee("You typed qq, a shortcut to quit ASAP.  Bye.\n");

                    exit;

                } elsif ($main::stdin eq 'l') {

                    $main::stdin = $menu[$#menu];

                    $gnrl_utils::LastMenuIndex = $#menu;

                }

                last if eval("\$main::stdin =~ m$regexp");

                print "\n'$main::stdin' is invalid.  Please try again.\a\n$hint\n";

            }

        } else { # not a menu

            my $blank_ok = $regexp eq '' || eval("'' =~ m$regexp") || $regexp =~ /^(efile|edir|epgm|efod)[?]$/;

            # Section: Non-menu input

            while (1) {

                print "\n$prompt\n";

                if ($stdin_default ne '') {

                    if ($blank_ok) {

                        print "Enter $main::null_str to override default with a null string.\n";

                    }

                    print "(default is $stdin_default):  ";

                } else {

                    print "(no default):  ";

                }

                $main::stdin = <STDIN>;

                chop($main::stdin);

                if ($main::stdin eq '') {

                    $main::stdin = $stdin_default;

                } elsif ($main::stdin eq $main::null_str && $blank_ok) {

                    $main::stdin = '';

                } elsif ($main::stdin eq $stdin_default) {

                    print(
                        "\n" .
                        ">> Save your fingers next time.\n" .
                        ">> You typed '$stdin_default' when that was the default.\n"  .
                        ">> It would have been easier to just press Enter.\n\a"
                    );

                }

                if ($regexp =~ /^(efile|edir|epgm|efod|!efod)[?]?$/ && $main::stdin =~ /^ *"(.*)" *$/) {

                    $main::stdin = $1; # remove double quotes around a file/dir name.

                }

                last if main::matches_regexp($main::stdin, $regexp);

                print "\n'$main::stdin' is invalid.  Please try again.\a\n$hint\n";

            }

        }

        main::di("'$main::stdin' entered in response to:\nDI: '$prompt'") if (!defined($quiet) || $quiet !~ /^q/);

    } else { # batch mode

        $main::stdin = $stdin_default;

        if (main::matches_regexp($main::stdin, $regexp)) {

            main::di("Default '$main::stdin' used in batch mode for response to:\nDI: '$prompt'");

        } else {

            main::tre("'$main::stdin' is invalid default for batch response to:\nRE: '$prompt'\nRE: $hint");

            main::expire("Stopped\a");;

        }

    }

    $main::stdin = 0 if $bool and $main::stdin =~ /^[NFnf0]/; # force the value to 0 since 'f' and 'y' evaluate to true

    return $main::stdin;

}

####################################################################################################

sub main::get_hint {

    my ($regexp_ref, $hint_ref, $menu_ref) = @_;

    @$menu_ref = ();

    if ($$regexp_ref eq 'bool') {

        $$hint_ref = "Enter y, n, t, or f" if main::is_false($$hint_ref);

    } elsif ($$regexp_ref eq 'edir') {

       $$hint_ref = "Your entry must name an existing directory." if main::is_false($$hint_ref);

    } elsif ($$regexp_ref eq 'efile') {

       $$hint_ref = "Your entry must name an existing file that is not a directory." if main::is_false($$hint_ref);

    } elsif ($$regexp_ref eq 'efod') {

       $$hint_ref = "Your entry must name an existing file or directory." if main::is_false($$hint_ref);

    } elsif ($$regexp_ref eq '!efod') {

       $$hint_ref = "Your entry must name a nonexistent file or directory." if main::is_false($$hint_ref);

    } elsif ($$regexp_ref eq 'epgm') {

       $$hint_ref = "Your entry must name an existing reachable executable." if main::is_false($$hint_ref);

    } elsif ($$regexp_ref eq 'edir?') {

       $$hint_ref = "Your entry must name an existing directory or else be blank." if main::is_false($$hint_ref);

    } elsif ($$regexp_ref eq 'efile?') {

       $$hint_ref = "Your entry must name an existing file or else be blank." if main::is_false($$hint_ref);

    } elsif ($$regexp_ref eq 'efod?') {

       $$hint_ref = "Your entry must name an existing file or directory or else be blank." if main::is_false($$hint_ref);

    } elsif ($$regexp_ref eq 'epgm?') {

       $$hint_ref = "Your entry must name an existing reachable executable or else be blank." if main::is_false($$hint_ref);

    } else {

        @$menu_ref = main::menu($$regexp_ref) if main::is_false($main::batch); # @menu will be filled w/ choices if $$regexp_ref appears to be a list of choices

        if (@$menu_ref) {

            ## main::tvi("Menu choices = @menu");

            $$hint_ref = "Select an integer from the menu." if main::is_false($$hint_ref);

        } elsif ($$regexp_ref && main::is_false($$hint_ref) ) {

            $$hint_ref = "Your entry must match the Perl pattern $$regexp_ref";

        }

    }

}

####################################################################################################

sub main::matches_regexp {

    my $Val    = shift;
    my $regexp = shift;

    # $regexp can be one of the following:
    # *   A standard Perl regular expression including delimiters and modifiers.  E.g.:  /^(Mon|Tue|Wed|Thu|Fri))$/i
    # *   bool   - input must be =~ /^[YNTFyntf01]/
    # *   efile  - input must name 1+ existing files (not dirs)
    # *   edir   - input must name 1+ existing dirs
    # *   efod   - input must name 1+ existing files or dirs
    # *  !efod   - input must name nonexistent file or directory
    # *   epgm   - input must name 1  executable program
    # *   efile? - input must name 1+ existing files         or be blank
    # *   edir?  - input must name 1+ existing dirs          or be blank
    # *   efod?  - input must name 1+ existing files or dirs or be blank
    # *   epgm?  - input must name 1  executable program     or be blank

    return 1 if ! $regexp;

    if ($regexp eq 'bool') {

        return !$Val || $Val =~ /^[yntf01]/i;

    } elsif ($regexp eq 'edir') {

       return main::DirsAllExist($Val);

    } elsif ($regexp eq 'efile') {

       return main::PlainFilesAllExist($Val);

    } elsif ($regexp eq 'efod') {

       return main::FilesAllExist($Val);

    } elsif ($regexp eq '!efod') {

       return !main::FilesAllExist($Val);

    } elsif ($regexp eq 'epgm') {

       return main::which($Val, 'warn');

    } elsif ($regexp eq 'edir?') {

       return ! $Val || main::DirsAllExist($Val);

    } elsif ($regexp eq 'efile?') {

       return ! $Val || main::PlainFilesAllExist($Val);

    } elsif ($regexp eq 'efod?') {

       return ! $Val || main::FilesAllExist($Val);

    } elsif ($regexp eq 'epgm?') {

       return ! $Val || main::which($Val, 'warn');

    } else {

       #--print qq(Checking if "$Val" =~ m$regexp\n);

       $Val = quotemeta($Val); # 09/14/14 this is needed if $Val is a DOS filename or otherwise has metacharacters that need escaping

       return (eval(qq("$Val" =~ m$regexp)));

    }

}

####################################################################################################

sub main::get_retry_ignore_abort {

    main::tre($_[0]) if defined($_[0]);

    my $ans = main::get_stdin(
        'What do you want to do about the above issue?',
        $main::batch? 'abort' : 'retry',                   # default
        main::MkMenu(qw(retry ignore abort))               # regexp
    );

    main::expire() if $ans eq 'abort';

    return $ans;

}

####################################################################################################

sub main::do_log_cmd { # system() or `` backticks on steroids

    # If you do not need to capture the output, use sub System() instead

    my $local_cmd;

    if (defined($_[0])) {

        $local_cmd = $_[0];

    } elsif (defined($main::cmd)) {

        $_[0] = $local_cmd = $main::cmd;

    } else {

        main::tre("Cannot execute null command\a");

        main::expire("Stopped\a");

    }

    ## main::log_file may have changed name at this point if there are multiple
    ## instances of the script running under UNIX.  Therefore, closing it, appending
    ## to it, and then reopening it may be invalid.  The command output must
    ## be placed in a different file and the latter must be appended to
    ## main::log_file.

    my $local_log = "$main::log_file.$$";

    $local_cmd .= " > $local_log 2>&1"; # OK in DOS

    my $hhmmss = main::HHMMSS();

    print("RI: \@T $hhmmss executing: $_[0]\n") if !defined($_[1]) or $_[1] !~ /^[qQ]/;

    main::ri("\@T $hhmmss executing: $local_cmd");

    my $cmd_result = `$local_cmd`;

    if (-s $local_log > 0) {

        open (file_mgr::local_log, $local_log);

        main::ri("Contents of $local_log:");

        while (<file_mgr::local_log>) {

            print main::log_file;

            print if defined($_[1]) && $_[1] =~ /^[eE]/

        }

        close (file_mgr::local_log);

    } else {

        main::ri("Nothing was put into $local_log");

    }

    unlink $local_log;

    if ($cmd_result =~ /\S/) {

        print("$cmd_result\n") if !defined($_[1]) or $_[1] !~ /^[qQ]/;

        main::ri($cmd_result);

    }

    if ($?) {

        $? /= 256;

        print("RE: Returned $?: $local_cmd\n") if !defined($_[1]) || $_[1] !~ /^Q/; # QUIET suppresses error reporting

        main::re("Returned $?: $local_cmd");

        #--main::tre("Returned $?: $local_cmd") if !defined($_[1]) || $_[1] !~ /^Q/; # Caps QUIET suppresses error reporting

    }

    return $?;

}
####################################################################################################

sub main::FormattedTime {  # optional Arg 1 = strftime format, Optional arg2 = UTC secs.

    my $Fmt = shift || "%a %b %d %H:%M:%S %Y"; # default is time and date string like "Fri Mar 15 21:50:20 2013"
    my $T   = shift || time();

    POSIX::strftime($Fmt, localtime($T));

}

####################################################################################################

sub main::HHMMSS {

    main::FormattedTime("%H:%M:%S", shift);

}

####################################################################################################

sub main::MMDDYYHHMMSS {

    main::FormattedTime("%m/%d/%y %H:%M:%S", shift);

}

####################################################################################################

sub main::printenv {

    my $key;

    foreach $key (sort keys %ENV) {

        main::trif("ENV: %-25s = %s", $key, $ENV{$key});

    }

    main::tri("Perl include path              = @INC");

    foreach $key (sort keys %INC) {

        main::trif("INC: %-25s = %s", $key, $INC{$key});

    }

    if ($main::is_dos) {

        main::tri ("program                        = $0  pid = $$ running under MSDOS");

    } else {

        main::trif("program                        = $0  pid = $$  parent pid = %d", getppid());

    }

    main::tri ("current working directory      = $main::cwd"      ) if defined($main::cwd      );
    main::tri ("current host                   = $main::this_host") if defined($main::this_host);

}

####################################################################################################

sub main::log_env {

    my $key;

    foreach $key (sort keys %ENV) {

        main::rif("ENV: %-25s = %s", $key, $ENV{$key});

    }

    main::ri("Perl include path              = @INC");

    foreach $key (sort keys %INC) {

        main::rif("INC: %-25s = %s", $key, $INC{$key});

    }

    if ($main::is_dos) {

        main::ri ("program                        = $0  pid = $$ running under MSDOS");

    } else {

        main::rif("program                        = $0  pid = $$  parent pid = %d", getppid());

    }

    main::ri ("current working directory      = $main::cwd") if defined($main::cwd);
    main::ri ("current host                   = $main::this_host") if defined($main::this_host);

}

####################################################################################################

sub main::csv_split {

    ## splits comma-separated-value strings.
    ## Assumes that the comma immediately follows the " for fields
    ## that are text strings.

    my @fields = split(/,/, $_[0]);
    ## split sees ALL commas, so strings with embedded commas
    ## must be spliced back together.

    my $field_index;

    for ($field_index = 0; $field_index <= $#fields; $field_index++) {

        if ($fields[$field_index] =~ /^"[^"]*$/) {
        ## then unbalanced " found

            $fields[$field_index] = join(',', @fields[$field_index .. $field_index + 1]);

            splice (@fields, $field_index + 1, 1); ## removes$fields[$field_index + 1]

            redo;

        } elsif ($fields[$field_index] =~ s/^"//) {
        ## then leading " was just stripped and it remains to strip trailing "

            $fields[$field_index] =~ s/"$//;

        }

        $fields[$field_index] =~ s/^[\s]*//; #strip leading  whitespace
        $fields[$field_index] =~ s/[\s]*$//; #strip trailing whitespace

    }

    @fields;

}

####################################################################################################

sub main::signature { ## computes 32-bit signature of @_  (more bits on 64 bit machine)

    my @input = split('', "@_"); ## get input chars split into array

    my $i;

    my $signature_ = 0;

    my $bit_shift = 0;

    my $val;

    for ($i = 0; $i < @input; $i++) {

        $val = ord($input[$i]);

        if ($bit_shift == 0) {

            $signature_ = $signature_ ^ $val;

        } else {

            ## printf "%x << $bit_shift = %x\n", $signature_ , $signature_ << $bit_shift;
            ##
            ## printf "%x >> (32 - $bit_shift) = %x\n", $signature_ , $signature_ >> (32 - $bit_shift);
            ##
            ## printf
            ##     "(%x << $bit_shift) ^ (%x >> (32 - $bit_shift)) = %x\n",
            ##     $signature_ ,
            ##     $signature_ ,
            ##     abs($signature_ << $bit_shift) ^ abs($signature_ >> (32 - $bit_shift));

            $signature_ =
                $val ^
                abs($signature_ << $bit_shift) ^
                abs($signature_ >> (32 - $bit_shift));

        }

        ## printf
        ##     "\$signature_ = %x after factoring in $input[$i] with val $val and bit_shift $bit_shift\n",
        ##     $signature_;

        $bit_shift = ($bit_shift + 7) % 32;

    }

    return $signature_;

}
####################################################################################################
sub main::rm_excess_files { ## removes old files fitting a certain pattern.  *ix sytems only

    return if @_ < 2;

    my ($keep_max, $file_pattern) = @_;

    my @old_files = glob($file_pattern);

    return if @old_files <= $keep_max;

    @old_files = split(' ', `ls -t @old_files`);

    my $temp;

    while (@old_files > $keep_max && @old_files > 0) {

        $temp = pop(@old_files);

        last if $temp !~ /\S/; ## just to be safe

        main::ri("removing old file $temp");

        unlink $temp;

    }

}
####################################################################################################
sub Launch {

    my $cmd = shift;

    my $dir;

    my $PID = fork();

    if (!defined($PID)) {

        main::tre("Cannot create process for $cmd: $?");

        print "Must abort.  Press enter.\n";

        <STDIN>;

        exit(-1);

    }

    if ($cmd =~ /^cd ([^;]+); *(.*)/) { # then execute the cd within Perl

        $dir = $1;

        $cmd = $2;

    } else {

        $dir = '';

    }

    if ($PID == 0) {

        # Then this is the child tine.

        chdir $dir if $dir;

        exec $cmd; # And we do not return unless there is an error.

    }

    my $quiet = shift || '';

    if ($PID) {

        main::trif("\@T %s pid $PID launched%s: $cmd", main::HHMMSS(), $dir ? " in $dir" : "") unless $quiet;

    } else {

        main::tre("Failed in Launch(): exec $cmd");

    }

    return $PID;

}
####################################################################################################
sub System {

    # If you need to capture the output use do_log_cmd() instead

    my $local_cmd;

    if (defined($_[0])) {

        $local_cmd = $_[0];

    } elsif (defined($main::cmd)) {

        $local_cmd = $main::cmd;

    } else {

        main::tre("Cannot execute null command\a");

        main::expire("Stopped\a");

    }

    my $hhmmss = main::HHMMSS();

    print("RI: \@T $hhmmss executing: $local_cmd\n") if !defined($_[1]) or $_[1] !~ /^[qQ]/;

    main::ri("\@T $hhmmss executing: $local_cmd");

    system($local_cmd);

    if ($?) {

        $? /= 256;

        print("RE: Returned $?: $local_cmd\n") if !defined($_[1]) || $_[1] !~ /^[qQ]/;

        main::re("Returned $?: $local_cmd");

    }

    return $?;

}

####################################################################################################

sub main::ExePath { # 02/14/11 return $ENV{PATH} split into an array.  04/19/13 see AnyPath()

    return main::AnyPath($ENV{PATH});

}

####################################################################################################

sub main::AnyPath { # 04/19/13 split any path-like string into an array

    my $Temp = shift;

    if ($main::is_dos) { # then DOS

        if ($Temp =~ /%systemroot%/i && defined($ENV{SYSTEMROOT})) {$Temp =~ s/%systemroot%/$ENV{SYSTEMROOT}/ig;}

        $Temp =~ s/;\s+$//; # remove a trailing ;

        return split (';', $Temp);

    } else { # Unix

        $Temp =~ s/:\s+$//; # remove a trailing :

        #$Temp =~ s# #\\ #g;

        return split (':', $Temp);

    }

}

####################################################################################################

sub main::which { # 02/14/11.  09/23/14 the -x bit if not tested if $is_dos

    my  $Warn  = $_[1] || '';

    if (! $_[0]) {

        main::trwf("Blank arg passed to sub which() @ %s:%d", __FILE__, __LINE__);

        return '';

    }

    my  $Exe   = main::tail($_[0]);
    my  $Path  = main::path($_[0]);
    my  $Dir;
    my  $PartExe;
    my  $FullExe;
    my  $Extn;
    my  @PathExtns = main::GetPathExtns() if $main::is_dos;
    my  $FullExeNotX = '';
    my  @Paths = $Path ? ($Path) : main::ExePath();

    #main::tri("Checking dirs '@Paths'");

    foreach $Dir (@Paths) {

        #main::tri("Checking dir '$Dir'");

        if ($main::is_dos) {

            $PartExe = "$Dir\\$Exe";

            foreach $Extn ('', @PathExtns) {

                $FullExe = $PartExe . $Extn;

                next if -d $FullExe;

                next if ! -e _;

                return $FullExe; # the -x bit is not significant for Windows

                #--09/23/14--# Under ActiveState Perl the -x file test is not reliable.  In fact, it always fails for me.
                #--09/23/14--return $FullExe if -x _;
                #--09/23/14--
                #--09/23/14--next if ! -e _;
                #--09/23/14--
                #--09/23/14--#main::trw("Found $FullExe$Extn, but x bit is not set, but this Perl may not test it correctly anyway, so am returning this result.") if $Warn;
                #--09/23/14--
                #--09/23/14--return $FullExe; # This is probably the lesser of two evils, given that -x always fails.

            }

        } else {

            $FullExe = "$Dir/$Exe";

            next if -d $FullExe;

            return $FullExe if -x _;

            next if ! -e _;

            main::trw("Found $FullExe, but x bit is not set") if $Warn;

        }

    }

    main::trw("No executble $Exe found in your path") if $Warn;

    return '';

}

####################################################################################################

sub main::GetPathExtns {

    if ($main::is_dos) {

        return split(';', lc($ENV{PATHEXT}));

    } else {

        return ();

    }

}

####################################################################################################

sub main::SetTitle { # 02/16/11

    autoflush STDOUT; printf("%c]2;%s\a", 27, $_[0]); # 27 is escape

}

####################################################################################################

sub main::is_true { # 03/19/13 Needed to correctly evaluate strings like 'false' or 'no'

    return defined($_[0]) && $_[0] && $_[0] !~ /^[0nNfF]/; # Don't use =~ /^[1yYtT]/

}

####################################################################################################

sub main::is_false { # 03/19/13 Needed to correctly evaluate strings like 'false' or 'no'

    return !main::is_true($_[0]);

}

####################################################################################################

sub main::RefPrint { # Easy interface to array_type and hash_type print and log functions

    $_[1] = ''    if ! defined $_[1];
    $_[2] = $_[1] if ! defined $_[2];

    #print("\nVI: Arg0: $_[0]\n");

    if    ($_[0] =~ /ARRAY\(0x/)    { array_type::print(@_);}
    elsif ($_[0] =~ /HASH\(0x/ )    { hash_type::print(@_);}
    elsif ($_[0] =~ /SCALAR\(0x/ )  { printf("$_[1]SCALAR ref to: %-60.60s%s\n", ${$_[0]}, length(${$_[0]}) > 60 ? "..." : "");}
    else                            { printf("$_[1]%-60.60s%s\n"               ,   $_[0] , length(  $_[0] ) > 60 ? "..." : "");}

}

####################################################################################################

sub main::RefLog { # Easy interface to array_type and hash_type print and log functions

    if (fileno(main::log_file)) {

        my $old_file_handle = select main::log_file;

        main::RefPrint(@_);

        select $old_file_handle;

    } else {

        print("RE: main::log_file not open in RefLog()\n");

    }

}

####################################################################################################

sub main::RefTee { # Easy interface to array_type and hash_type print and log functions

    main::RefPrint(@_);
    main::RefLog(@_);

}

####################################################################################################

sub main::RunInInterix { # then run $0 in sh (ksh just does not work right)

    my $cmd =  sprintf(qq(sh -c "\\\"%s\\\" @_"), main::NormalizePath($0, 'f'));

    $ENV{SHELL} = "/bin/sh";

    $ENV{PERL5LIB} = main::NormalizePath($ENV{PERL5LIB}, 'f') if defined($ENV{PERL5LIB});

    #print "PERL5LIB: $ENV{PERL5LIB}\n";

    print "Executing in sh: $cmd\n";

    system($cmd);

    # Using <STDIN> at this point hangs your window if $is_dos.  Same is true of backticks.

    # Hangs here for both ksh and sh: print "Press enter to continue ..."; <STDIN>;
    # Running job in background does not help.

    exit;

}

####################################################################################################

sub main::RunInDOS { # then run $0 in cmd.exe

    my $cmd =  sprintf(qq(cmd.exe /c "%s" @_), main::NormalizePath($0, 't'));

    delete $ENV{SHELL};

    $ENV{PERL5LIB} = main::NormalizePath($ENV{PERL5LIB}, 't') if defined($ENV{PERL5LIB});

    #print "PERL5LIB: $ENV{PERL5LIB}\n";

    print "Executing in CMD: $cmd\n";

    system($cmd);

    exit;

}

####################################################################################################

sub main::min {

    my $Min = 1e30;

    foreach (@_) {

        $Min = $_ if $_ < $Min;

    }

    return $Min;

}

####################################################################################################

sub main::max {

    my $Max = -1e30;

    foreach (@_) {

        $Max = $_ if $_ > $Max;

    }

    return $Max;

}

####################################################################################################

sub main::StartOver {

    my $cmd = "$0 ";

    foreach (@main::ARGV_Orig) {

        $cmd .=  quotemeta($_) . ' ';

    }

    main::tri("Executing $cmd");

    close main::log_file;

    if ($main::is_dos) {

        system($cmd); # exec() would be preferable, but it does not work right if $is_dos

    } else {

        exec($cmd);

    }

    exit; # not quit() here

}

####################################################################################################

sub main::PtnCt { # E.g., PtnCt('[/\\\\]', $SomeDir) return number of path separators in $SomeDir

    my $temp = $_[1];

    return $temp =~ s/$_[0]//g;

}

####################################################################################################
####################################################################################################

package hash_type;

sub new {

    my $class = shift;
    my $this = {};
    my $key;

    bless $this, $class;

    ## printf("Creating $this\n");

    while (defined($key = shift)) {

        if (@_) {

            $this->{$key} = shift;

            ## printf("    %-12s => %s\n", $key, $this->{$key});

        } else {

            main::tre("No value supplied for key '$key' while creating $this");

        }

    }

    $this;

}

####################################################################################################

sub print {

    my $this            = shift;
    my $hdr1            = shift || '';
    my $hdr2            = shift || $hdr1;
    my $noexpand        = shift || 0 ;
    my $member_ct       = scalar(keys %$this);
    my $key;
    my $value;
    my $hdr_plus        = "$hdr2|  ";
    my $max_key_width   = 1;

    printf("${hdr1}ref to %s w/ %d members%s\n", ref($this), $member_ct, $member_ct ? ':' : '.');

    foreach $key (keys  %$this) {

        $max_key_width = length($key) if length($key) > $max_key_width;

    }

    $max_key_width += 2;

    foreach $key (sort keys %$this) {

        printf("$hdr_plus%-${max_key_width}s => ", sprintf("{%s}", $key));

        if ($key =~ /parent/) {

            print "$this->{$key}\n";

            next;

        }

        my $ref = ref($this->{$key});

        $ref = "$this->{$key}" if $ref;

        if ($ref =~ /HASH\(0x/) {

            $noexpand? printf "ref to HASH w/ %d items\n", scalar keys %{$this->{$key}} : hash_type::print($this->{$key}, '', $hdr_plus);

        } elsif ($ref =~ /ARRAY\(0x/) {

            $noexpand? printf "ref to ARRAY w/ %3d items\n", scalar @{$this->{$key}} : array_type::print($this->{$key}, '', $hdr_plus);

        } elsif ($ref =~ /CODE\(0x/) {

           print "ref to CODE\n";

        } elsif ($ref =~ /SCALAR\(0x/) {

           printf("ref to SCALAR: %-60.60s%s\n", ${$this->{$key}}, length(${$this->{$key}}) > 60 ? "..." : "");

        } else {

            print "$this->{$key}\n";

        }

    }

}

####################################################################################################

sub tee {

    hash_type::print(@_);
    hash_type::log_it(@_);

}

####################################################################################################

sub log_it { ## log itself is a reserved name

    if (fileno(main::log_file)) {

        my $old_file_handle = select main::log_file;

        hash_type::print(@_);

        select $old_file_handle;

    }

}

####################################################################################################
####################################################################################################
####################################################################################################

package array_type;

sub new {

    my $type = shift;

    my $this = [];

    splice(@$this, 0, 0, @{$_[0]}) if defined($_[0]);

    bless $this, $type;

}

####################################################################################################

sub print {

    my $this = shift;
    my $hdr1 = shift || '';
    my $hdr2 = shift || $hdr1;

    printf("${hdr1}ref to %s w/ %d items%s\n", ref($this), scalar(@$this), scalar(@$this) ? ':' : '.');

    my $index = 0;

    my $value;

    my $hdr_plus = "$hdr2|  ";

    my $ref;

    for ($index = 0; $index < @$this; $index++) {

        next if !defined($this->[$index]);

        printf("$hdr_plus%-8d => ", $index);

        $ref = ref($this->[$index]);

        $ref = "$this->[$index]" if $ref;

        if ($ref =~ /HASH\(0x/) {

            hash_type::print($this->[$index], '', $hdr_plus);

        } elsif ($ref =~ /ARRAY\(0x/) {

            array_type::print($this->[$index], '', $hdr_plus);

        } elsif ($ref =~ /SCALAR\(0x/) {

            printf("ref to SCALAR: %-60.60s%s\n", ${$this->[$index]}, length(${$this->[$index]}) > 60 ? "..." : "");

        } else {

            print "$this->[$index]\n";

        }

    }

}

####################################################################################################

sub tee {

    array_type::print(@_);
    array_type::log_it(@_);

}

####################################################################################################

sub log_it { ## log itself is a reserved name

    if (fileno(main::log_file)) {

        my $old_file_handle = select main::log_file;

        array_type::print(@_);

        select $old_file_handle;

    }

}


####################################################################################################

####################################################################################################


####################################################################################################


####################################################################################################


####################################################################################################


####################################################################################################


####################################################################################################

1;
