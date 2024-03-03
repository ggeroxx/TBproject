## TBproject
Enter category: lezione di musica
leaf category?(y/n) n
Enter domain name: tipo
Enter description: des1

Enter category: lezione di teoria musicale
leaf category?(y/n) n
Enter domain name: materia
Enter description: des2
enter dad?
1. lezione di musica
enter relation type: teoria
end?

Enter category: lezione di solfeggio
leaf category?(y/n) y
Enter description: des3
enter dad?
1. lezione di musica
2. lezione di teoria musicale
enter relation type: solfeggio
end?

Enter category: lezione di storia della musica
leaf category?(y/n) y
Enter description: des4
enter dad?
1. lezione di musica
2. lezione di teoria musicale
enter relation type: storia della musica
end?

Enter category: lezione di uno strumento musicale
leaf category?(y/n) n
Enter domain name: strumento
Enter description: des5
enter dad?
0. lezione di musica
1. lezione di teoria musicale
enter relation type: strumento
end?

Enter category: lezione di chitarra
leaf category?(y/n) n
Enter domain name: livello
Enter description: des6
enter dad?
0. lezione di musica
1. lezione di teoria musicale
2. lezione di uno strumento musicale
enter relation type: chitarra
end?

Enter category: lezione di pianoforte
leaf category?(y/n) n
Enter domain name: livello
Enter description: des7
enter dad?
0. lezione di musica
1. lezione di teoria musicale
2. lezione di uno strumento musicale
3. lezione di chitarra
enter relation type: piano forte
end?

Enter category: lezione di pianoforte
leaf category?(y/n) y
Enter description: des8
enter dad?
0. lezione di musica
1. lezione di teoria musicale
2. lezione di uno strumento musicale
3. lezione di chitarra
4. lezione di pianoforte
enter relation type: principianti
end?

Enter category: lezione di pianoforte per principianti
leaf category?(y/n) y
Enter description: des9
enter dad?
0. lezione di musica
1. lezione di teoria musicale
2. lezione di uno strumento musicale
3. lezione di chitarra
4. lezione di pianoforte
enter relation type: principianti
end?

Enter category: lezione di pianoforte di difficoltà media
leaf category?(y/n) y
Enter description: des9
enter dad?
0. lezione di musica
1. lezione di teoria musicale
2. lezione di uno strumento musicale
3. lezione di chitarra
4. lezione di pianoforte
enter relation type: medio
end? yes

CREAZIONE TABELLA CATEGORIES
CREATE TABLE categories(  
    ID int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    field VARCHAR(25) NOT NULL,
    description VARCHAR(100) NOT NULL,
    hierarchyID int NOT NULL,
    IDConfigurator int NOT NULL,
    CONSTRAINT fk_categories FOREIGN KEY (IDConfigurator) REFERENCES configurators(ID)
) 

CREAZIONE TABELLA RELATIONSHIPSBETWEENCATEGORIES
CREATE TABLE relationshipsBetweenCategories(  
    parentID int NOT NULL,
    childID int NOT NULL,
    fieldType VARCHAR(25) NOT NULL,
    PRIMARY KEY (parentID, childID),
    CONSTRAINT fk1_relationshipsBetweenCategories FOREIGN KEY (parentID) REFERENCES categories(ID),
    CONSTRAINT fk2_relationshipsBetweenCategories FOREIGN KEY (childID) REFERENCES categories(ID)
) 

RINOMINA CHIAVI ESTERNE

ALTER TABLE districts DROP CONSTRAINT districts_ibfk_1
ALTER TABLE districts ADD CONSTRAINT fk_districts FOREIGN KEY (IDConfigurator) REFERENCES configurators(ID)

ALTER TABLE districtToMunicipalities DROP CONSTRAINT districtToMunicipalities_ibfk_1
ALTER TABLE districtToMunicipalities ADD CONSTRAINT fk1_districtToMunicipalities FOREIGN KEY (IDDistrict) REFERENCES districts(ID)

ALTER TABLE districtToMunicipalities DROP CONSTRAINT districtToMunicipalities_ibfk_2
ALTER TABLE districtToMunicipalities ADD CONSTRAINT fk2_districtToMunicipalities FOREIGN KEY (IDMunicipality) REFERENCES municipalities(ID)



