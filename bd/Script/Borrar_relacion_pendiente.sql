USE pictagram_bd;
DELETE FROM Relacion 
WHERE (nick_envia_pet = 'persona_no_amiga' AND nick_recibe_pet = 'perfil_usuario');