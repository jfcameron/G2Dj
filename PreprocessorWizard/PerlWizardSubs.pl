# #FILE_LAST_MODIFIED#
# This file contains subs needed by scripts auto-generated by the PerlWizard at www.rtbaileyphd.com/perlwizard

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

#######################################################################################

sub get_option_val { # called in all modes (ask_all interactive, regular interactive, batch) to get value from user or default

    my ($option_name, $option_description, $option_val_ref, $option_default_ref, $get_option_default_ref, $get_input_ref) = @_;

    return if $option_name =~ /^Placeholder/;

    if ($get_input_ref) {

        &$get_input_ref(@_); # special input handler supplied.  E.g., for sub get_ask_all_modifier (see below) for ask_all

    }

    if (defined($$option_val_ref)) {

        if ($$option_val_ref eq $main::null_str) {$$option_val_ref = '';}

        if (! matches_regexp($$option_val_ref, $regexps{$option_name})) {

            my @menu;

            get_hint(\$regexps{$option_name}, \$regexp_hints{$option_name}, \@menu); # in case client relies on default hints.

            tde("Current value '$$option_val_ref' is invalid for option $option_name");
            tde("Hint: $regexp_hints{$option_name}") if $regexp_hints{$option_name};

            expire() if $batch;

            $$option_val_ref = undef;

        }

    }

    if (!defined($$option_val_ref)) {

        &$get_option_default_ref() if !defined($$option_default_ref);

        if (
            $ask_all                                || # $ask_all overrides %prompt_fors and $batch
            ($prompt_fors{$option_name} && !$batch)    # normal interactive mode test
        ) { # then query the user

            $$option_val_ref = get_stdin(
                "Enter $option_name $option_description",
                $$option_default_ref,
                $regexps{$option_name},
                $regexp_hints{$option_name}
            );

        } else { # use the default

            $$option_val_ref = $$option_default_ref;

            # The default must be validated since the regexp might have been changed after the defaults_file was written
            if (!matches_regexp($$option_val_ref, $regexps{$option_name})) {

                tde("Default value '$$option_default_ref' is invalid for option $option_name, which must match this regexp:");
                tde("$regexps{$option_name}");
                tde("Hint: $regexp_hints{$option_name}") if $regexp_hints{$option_name};

                expire() if $batch;

                $$option_val_ref = get_stdin(
                    "Enter $option_name$option_description",
                    '', # the default
                    $regexps{$option_name},
                    $regexp_hints{$option_name}
                );

            }

        }

    }

    store_default("\$$option_name") if $is_storeds{$option_name} && !$batch;

    if ($option_name eq 'defaults_file') {

        load_defaults($defaults_file) if (!defined($defaults_file_default) || $defaults_file ne $defaults_file_default);

    } elsif ($switch_defaults_files{$option_name}) {

        switch_defaults_file($$option_val_ref);

    }

    # This forces the option to numeric 0 if it is a 'bool' type and the value begins with '0', 'n' or 'f':
    $$option_val_ref = 0 if $regexps{$option_name} eq 'bool' && is_false($$option_val_ref);

}

#######################################################################################

# Note:  If you have an old script that quite working with a sub ask_all redefined message, then replace:
#        $get_input_refs{ask_all} = \&get_ask_all_modifier;
# with:
#        $get_input_refs{ask_all} = \&get_ask_all_modifier;

sub get_ask_all_modifier {

    my ($option_name, $option_description) = @_;

    if (defined($ask_all)) { # -ask_all or -noask_all was on command line

        ; # do nothing since $ask_all is already known.

    } elsif (!defined($ask_all_default)) {

        $ask_all_default = $ask_all = 0;

    } elsif ($ask_all_default) { # $ask_all was left true from the previous run, so ask about $ask_all again

        $ask_all = get_stdin(
            "Enter $option_name$option_description",
            $ask_all_default,       # default
            'bool'
        );

    } else {

        $ask_all = 0; # only ask user for the typical options.

    }

    # At this point $ask_all will always be defined, so get_option_val() will skip the part where it tries to get $ask_all from the user or the default.
    # get_option_val() will still apply the regexp test and call store_default().

}

#######################################################################################

sub print_default { # only needed for PerlWizard scripts generated prior to 04/15/13

    my ($option_name, $option_default_ref, $get_option_default_ref) = @_;

    return if $option_name =~ /^Placeholder/;

    &$get_option_default_ref();

    printf "    -%-36s => %-36s\n", $option_name, $$option_default_ref;

}

#######################################################################################

sub print_default2 { # 04/15/13 replaces print_default

    my ($option_name, $option_default_ref, $get_option_default_ref, $option_val_ref) = @_;

    return if $option_name =~ /^Placeholder/;

    &$get_option_default_ref();

    printf "    -%-36s => %-36s\n", $option_name, $$option_default_ref;

    if ($help) {

        # Call get_option_val() since the normal get_option_val() call does not happen in the help mode, but is still needed
        # in case some default depends on the value of a preceding option.  Also, defaults_file switching is still needed:
        get_option_val($option_name, '', $option_val_ref, $option_default_ref, $get_option_default_ref); # non-interactive

    }

}

#######################################################################################

sub debug_log_option {

    return if !$debug;

    my ($option_name, $option_val_ref, $option_default_ref) = @_;

    printf(
        "VI: option %-24s val = %-24s  default = %-24s\n",
        $option_name,
        defined($$option_val_ref    ) ? $$option_val_ref     : '<undef>',
        defined($$option_default_ref) ? $$option_default_ref : '<undef>'
    );

}

#######################################################################################

sub log_option {

    my ($option_name, $option_val) = @_;

    return if $option_name =~ /^Placeholder/;

    #printf("VI: logging $option_name = %s\n", defined($option_val) ? $option_val : 'UNDEF');

    dif ("option -%-32s = %s", $option_name, $option_val);

}

#######################################################################################

sub switch_defaults_file {

    $defaults_file = $defaults_file_default if $help; # in case -help option is being executed

    #print("VI: \$defaults_file = '$defaults_file' in switch_defaults_file()\n");

    return if (!$defaults_file); # just in case a defaults_file is not being used.

    my $option_val = shift;

    # If, for example, the option is 'Customer' and $Customer eq "John Jones" and the old defaults file
    # was, "pwiz/MyScript.pl.someuser.defaults", then the new defaults file for the next option will be
    # "pwiz/MyScript.pl.someuser.John_Jones.defaults"

    $option_val =~ s/([^a-zA-Z0-9\-=~+@!#])+/_/g; # replace any file-name-problematic chars by underscores.

    $new_defaults_file = $defaults_file;

    $new_defaults_file =~ s=([.][^.]*)$=\.$option_val$1=; # insert ".$option_val" before the extension.

    if (is_false($batch) && !$help && $options_valid) {

        store_default("# switch to $new_defaults_file"); # creates a comment in old $defaults_file

    }

    load_defaults($new_defaults_file);

    #print("VI: switched defaults_file from $defaults_file to $new_defaults_file\n");

    $defaults_file = $new_defaults_file;

}

#######################################################################################

sub validate_option { # no longer used in new code, but keep it for old code.

    my ($option_name, $option_val) = @_;

    if ($regexps{$option_name} eq 'bool') {

        $$option_val = 0 if !defined($$option_val) || $$option_val =~ m/^[fnFN0]/; # Values like 'false' or 'no' must be forced to 0 or else will evaluate to true.

    } elsif ($regexps{$option_name} eq 'efile') {

        if (!FilesAllExist($$option_val)) {

            tde("$$option_val is invalid for option $option_name, which must name 1+ existing files");

            expire ("Stopped");

        }

    } elsif ($regexps{$option_name} eq 'edir') {

        if (!DirsAllExist($$option_val)) {

            tde("$$option_val is invalid for option $option_name, which must name 1+ existing directories");

            expire ("Stopped");

        }

    } elsif ($regexps{$option_name} eq 'epgm') {

        if (!which($$option_val)) {

            tde("$$option_val is invalid for option $option_name, which must name a program you can access from $cwd");

            expire ("Stopped");

        }

    } elsif ($regexps{$option_name} eq 'efile?') {

        if ($$option_val && !FilesAllExist($$option_val)) {

            tde("$$option_val is invalid for option $option_name, which must name 1+ existing files if not blank");

            expire ("Stopped");

        }

    } elsif ($regexps{$option_name} eq 'edir?') {

        if ($$option_val && !DirsAllExist($$option_val)) {

            tde("$$option_val is invalid for option $option_name, which must name 1+ existing directories if not blank");

            expire ("Stopped");

        }

    } elsif ($regexps{$option_name} eq 'epgm?') {

        if ($$option_val && !which($$option_val)) {

            tde("$$option_val is invalid for option $option_name, which must name a program you can access from $cwd if $option_name not blank");

            expire ("Stopped");

        }

    } elsif (
        $regexps{$option_name} ne '' &&
        eval("\$\$option_val !~ m$regexps{$option_name}")
    ) {

        tde ("$$option_val is invalid for option $option_name, which must match this regexp:");
        tde ("$regexps{$option_name}\a");
        tde ("Hint: $regexp_hints{$option_name}") if $regexp_hints{$option_name};

        expire ("Stopped");

    }

}

#######################################################################################

# If you have a complicated, grouping expression in %regexps, you can get the matched parts
# with the following sub.  For example, if you are using the Regex::Common::time module and
# defined the variable SomeDate and specified its validation expression as follows:
#       #^($RE{time}{mdy}{-keep})\$#
# then you can extract the month, day, and year like this:
#       GetRegexMatches('SomeDate', \@DateParts);
#       $Month = $DateParts[3]; # since Regex::Common::time doc says month is returned in $3
#       $Day   = $DateParts[4];
#       $Year  = $DateParts[5];
# Note that for the Regex::Common::time module you need to use # as the deliminter, not /
# For all Regex::Common $RE keys, {-keep} is the instruction to return the matches.
# Consult the Regex::Common documentation to determine what is returned in $1, $2, $3, etc.
# You do not need to be using Regex::Common modules to use GetRegexMatches(); you can
# use GetRegexMatches() with any of your own expressions that contain grouping parens.
sub GetRegexMatches {

    my $OptionName           = shift; # Name of option, NOT value of option
    my $ElementsArrayRef = shift;

    @$ElementsArrayRef = ();

    # If you get an uninitialized value warning re the following line, then you probably
    # called this sub like GetRegexMatches($MyVar, \@MyMatches) instead of GetRegexMatches('MyVar', \@MyMatches)

    if (defined($regexps{$OptionName})) {

        eval "\@\$ElementsArrayRef = (undef, \$$OptionName =~ m$regexps{$OptionName})";

        if (@$) {

            tref("Eval error at %s:%d: $@", __FILE__, __LINE__);

            return 0;

        }

        return @$ElementsArrayRef - 1; # Element 0 is not used since $0 is not returned by the m operator.

    } else {

        tre("\$regexps{$OptionName} is undefined.  Call GetRegexMatches('MyVar', \\\@MyMatches), not GetRegexMatches(\$MyVar, \\\@MyMatches)");

        return 0;

    }

}

#######################################################################################

sub CreateLogFile {

    my $log_size_trigger = shift;

    if (! -e $log_file or !$log_size_trigger or -s $log_file > $log_size_trigger) {

        $temp = NormalizePath(path($log_file));

        if ($temp && ! -e $temp) {

            mkdir_tree($temp);

            chmod 0775, $temp;

        }

        safely_create(*log_file, 1);

    } else {

        open log_file, ">> $log_file";

        print log_file  "\n", '-' x 79, "\n";

    }

    print("VI: log_file is $log_file\n") if $debug;

    ri ("$pgm run by $user at $this_host:$cwd $now");
    ri ("$pgm version is $pgm_version");

}

#######################################################################################

#######################################################################################

#######################################################################################

#######################################################################################

1;
