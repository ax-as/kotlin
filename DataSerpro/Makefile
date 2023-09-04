DTP_FILE = -f files/empregadosextraquadrose_requisitados-01_09_2023.csv
SERPRO_FILES = files/classificacao_ac.csv files/classificacao_ppp.csv files/classificacao_pcd.csv
ARGS = dataprev $(DTP_FILE) serpro $(SERPRO_FILES)
JAR_FILE = build/libs/DataSerpro-1.0-SNAPSHOT.jar

# Task para compilação com Gradle
compile:
	@echo "Compilando com Gradle..."
	@./gradlew build

# Task para execução do JAR
run:
	@echo "Executando aplicação com argumentos $(ARGS)"
	@java -jar $(JAR_FILE) $(ARGS)

# Task padrão para compilar e executar
all: compile run
