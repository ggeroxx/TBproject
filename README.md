CREATE TABLE `tbdb`.`districts` ( `ID` INT NULL , `nome` VARCHAR(50) NOT NULL , PRIMARY KEY (`ID`)) ENGINE = InnoDB;


CREATE TABLE districtToMunicipalities (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    idComprensorio INT,
    idComune INT,
    FOREIGN KEY (idComprensorio) REFERENCES districts(ID),
    FOREIGN KEY (idComune) REFERENCES municipalities(ID)
);