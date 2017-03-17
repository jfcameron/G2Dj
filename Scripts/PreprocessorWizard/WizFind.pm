#!/usr/bin/perl -w

# DO NOT EDIT: Recipients of this software may not alter lines marked "DO NOT EDIT"
# DO NOT EDIT: ALL RECIPIENTS/USERS/DEVELOPERS AGREE THAT THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
# DO NOT EDIT: INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
# DO NOT EDIT: IN NO EVENT SHALL RICHARD T. BAILEY, OTHER AUTHORS, OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
# DO NOT EDIT: WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
# DO NOT EDIT: OR OTHER DEALINGS IN THE SOFTWARE.
# DO NOT EDIT: All other lawful use is permitted.

# #FILE_LAST_MODIFIED#

package WizFind;

use File::Find;
use Cwd;
use strict;
use diagnostics; # This provides expanded error messages.
require 'gnrl_utils.pl';
require 'file_mgr.pl';

use vars qw(
    $DoFilesCt
    $DoDirsCt
    $SkipFilesCt
    $SkipFileSoftLinksCt
    $SkipDirsCt
    $ChangeDirsCt
    $SkipDirWinLinksCt
    $SkipDirSoftLinksCt
    $DoFileSoftLinksCt
    $ErrCt
    $StartDir
    %DirLinks
    $Cmd
    %Args
    $SkipDirTest
    $SkipFileTest
    $DoDirTest
    $DoDirExTest
    $DoFileTest
    $is_windows
    $Cwd
    $CaseSensitive
    $ShortenNames
    $temp
    @Stats
    $ReportErrs
    $ReportSummary
    $MaxDepth
);

###############################################################################################################

sub main::WizFind { # wrapper around File::find  Arg0 = \%NamedArgs   Arg[1] = $StartDir

    %Args     = %{$_[0]}; # Make a copy so the original is not destroyed, but note that this is a shallow copy and EditPtns() fixes that.
    $StartDir = $_[1] || '.';

    $Args{no_chdir} = 0; # Must be false or else the tests for invalid links get more complicated and costly.

    Initialize();

    #main::trif("In WizFind() StartDir = $StartDir   cwd = %s", cwd());

    main::RefLog(\%Args, 'RI: Edited Args: ');

    if ($Args{FindByDepth}) {

        main::ri(qq(Calling finddepth(\\\%Args, "$StartDir") ...));

        finddepth(\%Args, $StartDir);

    } else {

        main::ri(qq(Calling find(\\\%Args, "$StartDir") ...));

        find(\%Args, $StartDir);

    }

    if (defined($Args{Report})) {  &{$Args{Report}}  };

}

###############################################################################################################

sub Initialize {

    #main::trif("In Initialize cwd was     %s  StartDir was $StartDir", cwd());

    $StartDir = main::NormalizePath($StartDir);

    $is_windows = $main::is_dos || (defined($ENV{OS}) && $ENV{OS} =~ /Windows/i);
    # $is_windows will be true if running under Interix (maybe Cygwin too) and so cmd.exe is available and SYMLINKDs and JUNCTIONs can be found.

    $StartDir =~ s=\\=/=g; # Use forward slashes exclusively

    if (!defined($Args{wanted}              )) {$Args{wanted}           = \&Wanted      ;}
    if (!defined($Args{preprocess}          )) {$Args{preprocess}       = \&Preprocess  ;}
    if (!defined($Args{Report}              )) {$Args{Report}           = \&Report      ;}
    if (!defined($Args{TeeSummary}          )) {$Args{TeeSummary}       = 0             ;} # 1 means tee   summary report to terminal and log
    if (!defined($Args{TeeScanSummary}      )) {$Args{TeeScanSummary}   = 0             ;} # 1 means tee   top scanned dirs to terminal and log
    if (!defined($Args{LogScanSummary}      )) {$Args{LogScanSummary}   = 1             ;} # 1 means print top scanned dirs to log
    if (!defined($Args{LogScans}            )) {$Args{LogScans}         = 1             ;} # 1 means print all scanned dirs to log
    if (!defined($Args{LogDirSkips}         )) {$Args{LogDirSkips}      = 1             ;} # 1 means print all skipped dirs to log
    if (!defined($Args{LogFileSkips}        )) {$Args{LogFileSkips}     = 0             ;} # 1 means print all skipped dirs to log
    if (!defined($Args{FindByDepth}         )) {$Args{FindByDepth}      = 0             ;} # 1 means call File::Find::finddepth() vs File::Find::find()
    if (!defined($Args{TeeErrors}           )) {$Args{TeeErrors}        = 1             ;} # 0 means errors will be logged but not teed to terminal
    if (!defined($Args{MaxDepth}            )) {$Args{MaxDepth}         = 32            ;} #

    $DoFilesCt              = 0;
    $SkipFilesCt            = 0;
    $SkipFileSoftLinksCt    = 0;
    $DoDirsCt               = 0;
    $SkipDirsCt             = 0;
    $SkipDirWinLinksCt      = 0;
    $SkipDirSoftLinksCt     = 0;
    $DoFileSoftLinksCt      = 0;
    $ErrCt                  = 0;

    $Args{DoDirExPtns} = GetExPtns($Args{DoDirPtns});

    #--main::RefTee($Args{DoDirPtns}  , "VI: PreEdit  DoDirPtns  : ");
    #--main::RefTee($Args{DoDirExPtns}, "VI: PreEdit  DoDirExPtns: ");

    $SkipDirTest  = EditPtns($Args{SkipDirPtns} , 'SkipDirPtns' ); # Convert $Args{SkipDirPtns}  to regex form and compute $SkipDirTest
    $SkipFileTest = EditPtns($Args{SkipFilePtns}, 'SkipFilePtns'); # Convert $Args{SkipFilePtns} to regex form and compute $SkipFileTest
    $DoDirTest    = EditPtns($Args{DoDirPtns}   , 'DoDirPtns'   ); # Convert $Args{DoDirPtns}    to regex form and compute $DoDirTest
    $DoDirExTest  = EditPtns($Args{DoDirExPtns} , 'DoDirExPtns' ); # Convert $Args{DoDirExPtns}  to regex form and compute $DoDirExTest
    $DoFileTest   = EditPtns($Args{DoFilePtns}  , 'DoFilePtns'  ); # Convert $Args{DoFilePtns}   to regex form and compute $DoFileTest

    #--main::RefTee($Args{DoDirPtns}  , "VI: PostEditDoDirPtns  : ");
    #--main::RefTee($Args{DoDirExPtns}, "VI: PostEditDoDirExPtns: ");

    $CaseSensitive = main::is_true($Args{CaseSensitive});

    $ShortenNames = (-d "/dev/fs/C" && -d "/C"); # For Interix if /dev/fs/C and /C are equivalent (similar soft links for D, E, ... assumed)

    if ($StartDir =~ m=^/[A-Z]$= && $ShortenNames) {$StartDir = '/dev/fs' . $StartDir;} # $StartDir == '/C' does not work in Interix because it is a soft link

    $ReportErrs    = $Args{TeeErrors}  ? \&main::tdef : \&main::def;

    $ReportSummary = $Args{TeeSummary} ? \&main::trif : \&main::rif;

    $MaxDepth      = $Args{MaxDepth};

}

###############################################################################################################

sub EditPtns {

    return '' if ! defined($_[0]) || ! @{$_[0]};

    my $Ptn = '(';

    $_[0] = new array_type($_[0]); # Make a copy so the original is not destroyed

    foreach (sort @{$_[0]}) {

        s=\*\*=WizFindDotStar=g; # temp - see below

        s="= =g;  # replace '"' by ' ' to make it easier to use qw() to fill @$Args{...Ptns}

        s=\.=[.]=g;

        tr{\\}{/}; # replace backslashes by forward slashes

        tr{\?}{.}; # replace question marks by . for converting file wild cards to regex

        s=\*=[^/]*=g; # replace * by [^/]* for converting file wild cards to regex

        s=([\$\@\+])=\\$1=g; # Escape problematic characters

        s=/$=/.*=; # A trailing slash will give the wrong result, so replace by /.*

        s=WizFindDotStar=.*=g; # So ** gets replaced by .*

        if (/^\^/) { # orig began with '^'

            $_ .= '$' ; # postpend '$'

        } elsif (/^[a-z]:/i) { # orig began with something like C:

            $_ .= '$' ; # postpend '$'

        } elsif (m=^/=) { # orig began with '/'

            $_ .= '$' ; # postpend '$'

        } elsif (m=^\.\*=) { # orig began with '**' and now begins with .*

            $_ .= '$' ; # postpend '$'

        } else { # prepend '/' and postpend '$'

            $_ = '/' . $_ . '$';

        }

        $Ptn .= "$_|";

    }

    $Ptn =~ s/.$/)/;

    my $Test = "m=$Ptn=" . ($CaseSensitive ? '' : 'i');

    main::rif("%-16s  $Test", "$_[1]Test:");

    $Test;

}

###############################################################################################################

sub GetExPtns { # If @{$_[0]} contains "aa/bb/cc", the returned array will contain "aa" , "aa/bb" , "aa/bb/cc"

    my %ExPtns;
    my @Ptns = defined ($_[0]) ? @{$_[0]} : (); # Make a copy so original is not destroyed

    foreach (@Ptns) {

        while (1) {

            $ExPtns{$_}++ if $_;

            last if $_ !~ s=/[^/]*$==;

        }

    }

    my @ExPtns = (sort keys %ExPtns);

    return \@ExPtns; # Perl does reference counting so it is OK to return a ref like this.

}

###############################################################################################################

sub Preprocess {

    # Even though this sub is called Preprocess, File::find makes its very first call to Wanted() and passes it $StartDir.
    # The 1st call to Preprocess() is for $StartDir.
    # In here $File::Find::dir is identical to $File::Find::name

    my $Cmd;
    my @Temp = split('/', $File::Find::dir);
    my $Depth = scalar @Temp;

    $Cwd = cwd();

    $Cwd =~ s=^/dev/fs== if $ShortenNames;

    my $DosDirList = '';

    if ($is_windows) {

        $Cmd = qq(cmd.exe /c dir /A:L "." 2>&1);
        # 2>&1 DOES work for cmd.exe and puts 'File Not Found' in $DosDirList where it can be tested

        $DosDirList = `$Cmd`;

        #main::trif("CWD = $Cwd FFD = $File::Find::dir  Cmd = '%s'  DosDirList = \n$DosDirList", $Cmd);

        $DosDirList = '' if $DosDirList =~ /File Not Found/;

    }

    #main::ri("DosDirList of $File::Find::name\n$DosDirList");

    #main::trif("Pre: Depth:$Depth Cwd: %-60s dir: %-60s", $Cwd, $File::Find::dir);

    $temp = quotemeta($Cwd);

    my $DoDir   = !$DoDirTest   || (($Cmd = qq("$temp" =~ $DoDirTest  )) && eval($Cmd));
    my $DoDirEx = !$DoDirExTest || (($Cmd = qq("$temp" =~ $DoDirExTest)) && eval($Cmd));

    my $Action  =
        $DoDir      ? 'Scanning'    :
        $DoDirEx    ? 'Passing '    :
        'Skipping'                  ;
    my $Explain =
        $DoDir      ? ''            :
        $DoDirEx    ? ''            :
        '(!$DoDirExTest)'           ;
    my $SummaryReporter =
        $DoDir      ? \&main::tri   :
        $DoDirEx    ? \&main::ri    :
        \&main::ri                  ;

    my @TrimmedList = ();

    if ($File::Find::dir !~ m=$StartDir.*/.+/.+/=) {

        $temp = sprintf("$Action  dir  %-80s %s", "$Cwd + subdirs", $Explain);

        if ($Args{TeeScanSummary}) {

            &{$SummaryReporter}($temp); # Just show 3 levels of directories to STDOUT

        } elsif ($Args{LogScanSummary} || $Args{LogScans}) {

            main::ri($temp); # Just show 3 levels of directories to log

        }

    } elsif ($Args{LogScans}) {

        main::ri ("$Action  dir  $Cwd$Explain");

    }

    return @TrimmedList if !$DoDirEx;

    foreach (@_) {

        my $ShortName = $_;

        next if ($ShortName eq '.' or $ShortName eq '..');

        my $FullFileName = "$Cwd/$ShortName";

        if ($DosDirList) { # do not use the -d test here in case the JUNCTION or SYMLINKD is broken

            my $temp = quotemeta($ShortName);

            if ($DosDirList =~ /<(JUNCTION|SYMLINKD)> *$temp \[(.*)\]/) {

                my $LinkTarget = main::NormalizePath($2);

                main::rif("Skipping  dir  %-80s %s -> $LinkTarget", $FullFileName, $1) if $Args{LogDirSkips};

                $SkipDirWinLinksCt++;

                if (!-e $LinkTarget) {

                    &{$ReportErrs}("$1  dir \"$FullFileName\" links to nonexistent file \"$LinkTarget\"");

                    $ErrCt++;

                }

                next;

            }

        }

        # 03/11/15 This sub happened, but still lstat failed:
        #$FullFileName =~ s{([?\[\]])}{\\$1}g; # 03/11/15 FreeFileSync actually puts question marks and square brackets in log file names.
        # 03/11/15 This totally breaks lstat: $FullFileName = quotemeta($FullFileName);
        # main::tri("lstat-ing $FullFileName");

        @Stats = lstat $FullFileName; # lstat works for fields (size, atime, mtime, ctime) if $is_dos (at least for ActiveState Perl)
        # stat  0 dev      device number of filesystem
        # stat  1 ino      inode number
        # stat  2 mode     file mode  (type and permissions)
        # stat  3 nlink    number of (hard) links to the file
        # stat  4 uid      numeric user ID of file's owner
        # stat  5 gid      numeric group ID of file's owner
        # stat  6 rdev     the device identifier (special files only)
        # stat  7 size     total size of file, in bytes
        # stat  8 atime    last access time in seconds since the epoch
        # stat  9 mtime    last modify time in seconds since the epoch
        # stat 10 ctime    inode change time (NOT creation time!) in seconds since the epoch
        # stat 11 blksize  preferred block size for file system I/O
        # stat 12 blocks   actual number of blocks allocated

        # This is not needed:  if (!@Stats) {...}

        #main::tri("Cwd: $Cwd  \$ShortName: $ShortName  FullFileName: $FullFileName");

        if (-l _) { # Can use _ here because of the above lstat call

            next if ! $DoDir;

            my $LinkTarget = readlink $ShortName;

            #main::ri("Found soft link $FullFileName -> $LinkTarget");

            if (!-e $LinkTarget) {

                &{$ReportErrs}("Broken soft link: %-80s -> %s", $FullFileName, $LinkTarget);

                $ErrCt++;

                next;

            } elsif (-d $ShortName) {

                $SkipDirSoftLinksCt++;

                #main::ri("Found: Link $ShortName");

                main::rif("Skipping  dir  %-80s *ix link -> %s", $FullFileName, $LinkTarget) if $Args{LogDirSkips};

                next;

            }

            $temp = quotemeta($FullFileName);

            if ($SkipFileTest && ($Cmd = qq("$temp" =~ $SkipFileTest)) && eval($Cmd)) {

                #main::ri("SkipFilePtnsTest: $Cmd");

                main::rif("Skipping slink %-80s (SkipFilePtnsTest)", $FullFileName) if $Args{LogFileSkips};

                $SkipFileSoftLinksCt++; # SkipFilePtnsTest

                next;

            }

            if ($DoFileTest && ($Cmd = qq("$temp" !~ $DoFileTest)) && eval($Cmd)) {

                #main::ri("DoFilePtnsTest: $Cmd");

                main::rif("Skipping slink %-80s (!DoFilePtnsTest)", $FullFileName) if $Args{LogFileSkips};

                $SkipFileSoftLinksCt++; # !DoFilePtnsTest

                next;

            }

            $temp = $ShortName; # so poorly-written callback does not mess us up.
            &{$Args{DoSoftLinkFile}}(\$FullFileName, \$temp, \@Stats, -s _, -M _, \$LinkTarget) if ($Args{DoSoftLinkFile}); # DoSoftLinkFile callback call

            $DoFileSoftLinksCt++;

        } elsif (-d _) {

            $temp = quotemeta($FullFileName); # So SkipDirPtns is applied to the full directory name.

            if ($SkipDirTest && ($Cmd = qq("$temp" =~ $SkipDirTest)) && eval($Cmd)) {

                #main::ri("SkipDirPtnsTest: $Cmd");

                main::rif("Skipping  dir  %-80s (SkipDirPtnsTest)", $FullFileName) if $Args{LogDirSkips};

                $SkipDirsCt++; # SkipDirTest

                next;

            } elsif ($Depth >= $MaxDepth) {

                main::rif("Skipping  dir  %-80s (MaxDepth)", $FullFileName) if $Args{LogDirSkips};

                $SkipDirsCt++; # SkipDirTest

                next;

            }

            $temp = $ShortName; # so poorly-written callback does not mess us up.
            &{$Args{DoDir}}(\$FullFileName, \$temp, \@Stats, -s _, -M _) if ($Args{DoDir});  # DoDir callback call

            $DoDirsCt++;

        } elsif (-f _) {

            next if ! $DoDir;

            $temp = quotemeta($FullFileName);

            if ($SkipFileTest && ($Cmd = qq("$temp" =~ $SkipFileTest)) && eval($Cmd)) {

                #main::ri("SkipFilePtnsTest: $Cmd");

                main::rif("Skipping  file %-80s (SkipFilePtnsTest)", $FullFileName) if $Args{LogFileSkips};

                $SkipFilesCt++; # SkipFilePtnsTest

                next;

            }

            if ($DoFileTest && ($Cmd = qq("$temp" !~ $DoFileTest)) && eval($Cmd)) {

                #main::ri("DoFilePtnsTest: $Cmd");

                main::rif("Skipping  file %-80s (!DoFilePtnsTest)", $FullFileName) if $Args{LogFileSkips};

                $SkipFilesCt++; # !DoFilePtnsTest

                next;

            }

            $temp = $ShortName; # so poorly-written callback does not mess us up.
            &{$Args{DoRegularFile}}(\$FullFileName, \$temp, \@Stats, -s _, -M _) if ($Args{DoRegularFile});  # DoRegularFile callback call

            $DoFilesCt++; # -f _ + failed skip tests

        } elsif (/hiberfil[.]sys|pagefile[.]sys/)  {

            # ignore it

            next;

        } else {

            &{$ReportErrs}("Cannot get status of '$FullFileName' in Cwd '$Cwd': $!");

            $ErrCt++;

            next;

        }

        push @TrimmedList, $ShortName;

    }

    #main::trif("Returning TrimmedList: @TrimmedList");

    @TrimmedList;

}

###############################################################################################################

sub Wanted {

    # The first callback from File::find() is actually to Wanted(), not Preprocess().  In this first call
    # File::find() passes a directory, which is $StartDir and File::find() has ALREADY cd'd to $StartDir.
    # This is in contrast to all later calls where it passes in a directory.  In those cases, File::find()
    # will NOT have cd'd to $File::Find::name.
    # In other words for the very 1st call cwd() and $File::Find::name would be equivalent, but
    # for all other calls for which (-d $File::Find::name) is true, cwd()/$_ and $File::Find::name would be equivalent.
    # This can cause some confusion, so be careful.
    # For Preprocess() the cd will always already have taken place so the situation there is less confusing.

    # Even if $main::is_dos, File::find() will convert backslashes to forward slashes, at least for ActiveState Perl.

    #main::trif("Check %-4s short name: '%s' long name: '$File::Find::name'", (-d $_ ? 'dir ' : -f _ ? 'file' : '????'), $_);

}

###############################################################################################################

sub Report {

    &{$ReportSummary}("DoFilesCt               = %6d - regular files, not dirs"                         , $DoFilesCt               ) if ($DoFilesCt           );
    &{$ReportSummary}("DoFileSoftLinksCt       = %6d - *ix file links"                                  , $DoFileSoftLinksCt       ) if ($DoFileSoftLinksCt   );
    &{$ReportSummary}("SkipFilesCt             = %6d - egrep FilePtns $main::log_file"                  , $SkipFilesCt             ) if ($SkipFilesCt         );
    &{$ReportSummary}("SkipFileSoftLinksCt     = %6d - egrep FilePtns $main::log_file"                  , $SkipFileSoftLinksCt     ) if ($SkipFileSoftLinksCt );
    &{$ReportSummary}("DoDirsCt                = %6d - regular dirs"                                    , $DoDirsCt                ) if ($DoDirsCt            );
    &{$ReportSummary}("SkipDirsCt              = %6d - egrep DirPtns $main::log_file"                   , $SkipDirsCt              ) if ($SkipDirsCt          );
    &{$ReportSummary}("SkipDirWinLinksCt       = %6d - egrep \"Skip.*(JUNC|SYM)\" $main::log_file"      , $SkipDirWinLinksCt       ) if ($SkipDirWinLinksCt   );
    &{$ReportSummary}("SkipDirSoftLinksCt      = %6d - egrep \"Skip.*ix\" link $main::log_file"         , $SkipDirSoftLinksCt      ) if ($SkipDirSoftLinksCt  );
    &{$ReportSummary}("ErrCt                   = %6d - egrep ^.E: $main::log_file\n"                    , $ErrCt                   ) if ($ErrCt               );
    &{$ReportSummary}("ErrCt                   = %6d\n"                                                 , $ErrCt                   ) if ($ErrCt == 0          );
    &{$ReportSummary}("To see args/options actually used by WizFind:  egrep \"^RI: Edited\" $main::log_file\n");

}

###############################################################################################################

###############################################################################################################

###############################################################################################################

###############################################################################################################

###############################################################################################################

