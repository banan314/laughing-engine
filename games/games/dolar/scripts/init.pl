use warnings;

use feature 'say';

sub buildInit {
	my ($x, $y, $piece) = @_;
	$x = "$x"; $y = "$y";
	return "(init(cell " .$x. ' ' .$y. ' ' .$piece. ')) ';
}

my @players = ("white", "black");
my $SIDE_LENGTH = 9;
my $x = 1; my $y = 1;

say '(init (step 1))';

say buildInit($x, $y, $players[0]);

for(; $x < $SIDE_LENGTH; $x++) {
	for(; $y <= $SIDE_LENGTH; $y++) {
		say	buildInit($x, $y, "empty");
	}
	$y = 1;
}
for(; $y < $SIDE_LENGTH; $y++) {
	say	buildInit($x, $y, "empty");
}

say buildInit($x, $y, $players[1]);

say '(init(control white))';