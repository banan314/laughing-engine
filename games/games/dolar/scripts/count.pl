use warnings;

use feature 'say';

my $player = shift;

my $min;
my $max;

if ($player eq "black") {
	$min = 1; $max = 4;
} elsif ($player eq "white") {
	$min = 6; $max=9;
} else {
	die "wrong input! white or black";
}

sub nextSquare {
	my ($x, $y) = @_;
	if ($x == $max) {
		$x = $min;
		$y++;
	} else {
		$x++;
	}
	return ($x, $y);
}

sub lastSquare {
	($x, $y) = @_;
	return $x==$max && $y==$max;
}

sub buildCount {
	($x, $y) = @_;
	my @nextCoordinates = nextSquare($x, $y);

	my $def = "<= (count $x $y ?num)";
	my $checkCell = "(true (cell $x $y $player))";
	my $succ = "\t(succ ?prevnum ?num)";
	my $next = "\t(count $nextCoordinates[0] $nextCoordinates[1] ?prevnum)";

	if (lastSquare($x, $y)) {
		return join "\n", ("("."<= (count $x $y 1)", "\t(true (cell $max $max $player))".")", "",
			"("."<= (count $x $y 0)", "\t(not (true (cell $max $max $player)))".")");
	} else {
		my $pieceProp = join "\n", ("(".$def, "\t$checkCell", $succ, $next.")");
		$next =~ s/prevnum/num/; #replace prevnum with num, cause we don't increment
		my $emptyProp = join "\n", ("(".$def, "\t(not ". $checkCell . ")", $next.")");
		return join "\n", ($pieceProp, $emptyProp);
	}
}

my ($x, $y) = ($min, $min);
do {
	say buildCount($x, $y) . "\n";
	($x, $y) = nextSquare($x, $y);
}until lastSquare($x, $y);
say buildCount($x, $y) . "\n";
