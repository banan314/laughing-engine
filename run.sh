javac -d out \
--module-source-path org.ggp.base/src/main/java:pl.edu.prz.klopusz/dolar-app/src/main/java \
 $(find org.ggp.base/src/main/java -name *.java) \
 $(find pl.edu.prz.klopusz/dolar-app/src/main/java -name *.java)  \
 -verbose

# javac -d out \
# --module-source-path \
# org.ggp.base/src/main/java/module-info.java $(find org.ggp.base/src/main/java -name *.java) \
# pl.edu.prz.klopusz/dolar-app/src/main/java/module-info.java $(find pl.edu.prz.klopusz/dolar-app/src/main/java -name *.java)  
