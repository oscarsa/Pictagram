DROP TRIGGER trigger_borrado_fotos;

USE pictagram_bd;
DELIMITER \\
CREATE TRIGGER trigger_borrado_fotos BEFORE DELETE ON Fotos
FOR EACH ROW
BEGIN	
	SET SQL_SAFE_UPDATES=0;
	DELETE FROM Comentario 
	WHERE idFoto = old.idFoto;
	SET SQL_SAFE_UPDATES=1;
END \\
DELIMITER ;