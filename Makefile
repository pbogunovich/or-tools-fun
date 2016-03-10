PROGRAM=MaximumFlowTest

.PHONY: compile
compile:
	mkdir -p target
	javac -d target -cp "lib/*" src/pete/*

.PHONY: run
run: compile
	java -Djava.library.path=lib -cp "target:lib/*" pete.${PROGRAM}

.PHONY: clean
clean:
	rm -rf ./target
