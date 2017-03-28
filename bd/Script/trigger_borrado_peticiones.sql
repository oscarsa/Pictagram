DROP TRIGGER trigger_borrado_peticiones;

USE pictagram_bd;
DELIMITER \\
CREATE TRIGGER trigger_borrado_peticiones AFTER DELETE ON Relacion
FOR EACH ROW
BEGIN	
	SET SQL_SAFE_UPDATES=0;
	INSERT INTO Amistades (nickname, Amigo) VALUES (old.nick_recibe_pet,old.nick_envia_pet);
	SET SQL_SAFE_UPDATES=1;
END \\
DELIMITER ;