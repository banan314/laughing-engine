use warnings;

use feature 'say';
use Config::IniFiles;

my $cfg = Config::IniFiles->new( -file => "./configuration.ini" );

my @players = ($cfg->val('role', 'player1'), $cfg->val('role', 'player2'));

say "(role $players[0])";
say "(role $players[1])";
