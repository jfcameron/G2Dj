#!/usr/bin/perl -w

# #FILE_LAST_MODIFIED#

my $ArgIndex;
my $Debug = 0;

foreach $ArgIndex ( 0 .. $#ARGV) {

    printf "ARGV[%2d] = %-20s (as received)\n", $ArgIndex, $ARGV[$ArgIndex] if $Debug;

    $ARGV[$ArgIndex] =~ s="=\\"=g;

    if ($ARGV[$ArgIndex] =~ /[ \^]/) { # then it has a space or ^, so put it in double quotes

        $ARGV[$ArgIndex] = qq("$ARGV[$ArgIndex]");

    }

    printf "ARGV[%2d] = %-20s (as passed) \n\n", $ArgIndex, $ARGV[$ArgIndex] if $Debug;

}

my $cmd = "@ARGV";

print "\nExecuting: $cmd\n\n" if $Debug;

system("cmd.exe /c pause") if $Debug;

system($cmd);

print "\nDone     : $cmd\n\n" if $Debug;

# This fails in some situations:  print "Press enter to finish   " ; <STDIN>;

system("cmd.exe /c pause"); # This appears to work in all cases

