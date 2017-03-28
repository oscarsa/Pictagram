USE pictagram_bd;
DELIMITER \\
CREATE TRIGGER trigger_borrado_usuario1 BEFORE DELETE ON Usuarios
FOR EACH ROW
BEGIN	
	SET SQL_SAFE_UPDATES=0;
	SET FOREIGN_KEY_CHECKS=0;
	DELETE FROM Amistades 
	WHERE nickname = old.nickname OR Amigo = old.nickname;
	SET FOREIGN_KEY_CHECKS=1;
	SET SQL_SAFE_UPDATES=1;
END \\
DELIMITER ;