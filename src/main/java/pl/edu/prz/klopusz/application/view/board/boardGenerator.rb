arcWidth=5.0;
arcHeight=5.0;

fill="DODGERBLUE";

layoutX=80.0;
layoutY=43.0;

onMouseClicked="#onSquareClick";

stroke="BLACK";
strokeType="INSIDE";

width=45.0;
height=44.0;

line = (1..9);
leftLayoutX = layoutX;

for i in line
  for j in line
    puts "<Rectangle arcHeight=\"#{arcHeight}\" arcWidth=\"#{arcWidth}\" fill=\"#{fill}\" height=\"#{height}\" layoutX=\"#{layoutX}\" layoutY=\"#{layoutY}\" onMouseClicked=\"#{onMouseClicked}\" stroke=\"#{stroke}\" strokeType=\"#{strokeType}\" width=\"#{width}\" />";
    layoutX += width;
  end
  layoutX = leftLayoutX;
  layoutY += height;
end
