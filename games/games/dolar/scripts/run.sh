#!/bin/bash

finalFile=out/dolar.kif
if [ -e finalFile ]; then
	rm $finalFile
fi
touch $finalFile

cat stub/header.kif >> $finalFile
perl role.pl >> $finalFile
cat stub/base.kif >> $finalFile
perl index.pl >> $finalFile
cat stub/piece.kif >> $finalFile
perl init.pl >> $finalFile
cat stub/legal.kif >> $finalFile
cat stub/next.kif >> $finalFile
cat stub/goal.kif >> $finalFile
cat stub/terminal.kif >> $finalFile
cat stub/neighbor.kif >> $finalFile
perl succ.pl >> $finalFile
perl score.pl >> $finalFile
perl count.pl black >> $finalFile
perl count.pl white >> $finalFile