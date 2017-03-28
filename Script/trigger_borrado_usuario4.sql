USE pictagram_bd;
DELIMITER \\
CREATE TRIGGER trigger_borrado_usuario4 BEFORE DELETE ON Usuarios
FOR EACH ROW
BEGIN	
	SET SQL_SAFE_UPDATES=0;
	SET FOREIGN_KEY_CHECKS=0;
	DELETE FROM Fotos 
	WHERE nickname = old.nickname;
	SET FOREIGN_KEY_CHECKS=1;
	SET SQL_SAFE_UPDATES=1;
END \\
DELIMITER ;
