ALTER TABLE `message_receiver` ADD CONSTRAINT `FK_message_receiver_message` FOREIGN KEY `FK_message_receiver_message` (`MESSAGE_ID`)
    REFERENCES `message` (`MESSAGE_ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
 ADD CONSTRAINT `FK_message_receiver_member` FOREIGN KEY `FK_message_receiver_member` (`RECEIVER_MEMBER_ID`)
    REFERENCES `member` (`MEMBER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION;
    
ALTER TABLE `message_attachment` ADD CONSTRAINT `FK_message_attachment_message` FOREIGN KEY `FK_message_attachment_message` (`MESSAGE_ID`)
    REFERENCES `message` (`MESSAGE_ID`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT;

ALTER TABLE `USER_DEFINED_VALUES` ADD CONSTRAINT UNIQUE KEY `user_defined_value_uk1` (`user_defined_values_category`,`user_defined_values_value`);


ALTER TABLE `TEST_TUTORIAL_MODULES` ADD CONSTRAINT `FK_MODULE_DOCUMENT_UK1` FOREIGN KEY `FK_MODULE_DOCUMENT_UK1` (`TEST_TUTORIAL_DOCUMENT_ID`)
    REFERENCES `TEST_TUTORIAL_DOCUMENT` (`TEST_TUTORIAL_DOCUMENT_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION;