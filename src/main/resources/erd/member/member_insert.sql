INSERT INTO member (id, loginId, password, name, phone, email, birth_date)
	VALUES (member_id_SEQ.NEXTVAL, '아이디1', '비밀번호1', '이름1', '111-1111-1111', '메일1@test.com', TO_DATE('2000-01-01', 'YYYY-MM-DD'));