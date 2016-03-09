PROGRAM=MaxFlowTest

.PHONY: run
run:
	mkdir -p target
	javac -d target -cp lib/com.google.ortools.jar src/pete/${PROGRAM}.java
	java -Djava.library.path=lib -cp target:lib/com.google.ortools.jar \
    pete.${PROGRAM}

.PHONY: clean
clean:
	rm -rf ./target
