USE pictagram_bd;
UPDATE Usuarios SET nickname ='nickname_valor_nuevo', contra = 'contra_valor_nuevo' and email = 'email_valor_nuevo'
WHERE nickname = 'nickname_valor_viejo' AND contra = 'contra_vieja'; 