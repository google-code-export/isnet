ALTER TABLE `TEST_TUTORIAL_QUESTION_ANS` ADD CONSTRAINT `FK_test_tutorial_question_ans_module` FOREIGN KEY `FK_test_tutorial_question_ans_module` (`TEST_TUTORIAL_MODULES_ID`)
    REFERENCES `TEST_TUTORIAL_MODULES` (`test_tutorial_modules_id`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION;
