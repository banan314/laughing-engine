use warnings;

use feature 'say';
use Config::IniFiles;

my $cfg = Config::IniFiles->new( -file => "./configuration.ini" );

my $squareNumber = ($cfg->val('game', 'maxIndex'));

sub buildSucc {
	($this, $succ) = @_;
	return "(succ $this $succ)";
}

say '
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;; Data ;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
';

say buildSucc($_, $_+1) foreach (0..($squareNumber * $squareNumber));
