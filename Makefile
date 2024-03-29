run: src/Cellule.java src/Grille.java src/Jeu.java
	javac src/Cellule.java src/Grille.java src/Motifs.java src/Jeu.java -d bin && java -cp bin Jeu

clean:
	rm -r bin/
