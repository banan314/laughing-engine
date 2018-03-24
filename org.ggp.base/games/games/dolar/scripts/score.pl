use warnings;

use feature 'say';
use Config::IniFiles;

my $cfg = Config::IniFiles->new( -file => "./configuration.ini" );

my @players = ($cfg->val('role', 'player1'), $cfg->val('role', 'player2'));

say '
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; score-counting functions
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
';

say "(<= (score $players[0] ?score)";
say  "  (count 6 6 ?score))";

say "(<= (score $players[1] ?score)";
say "    (count 1 1 ?score))";