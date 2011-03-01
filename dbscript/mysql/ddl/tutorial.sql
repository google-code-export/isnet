ALTER TABLE `myc2i`.`test_tutorial_question_ans` ADD CONSTRAINT `FK_test_tutorial_question_ans_module` FOREIGN KEY `FK_test_tutorial_question_ans_module` (`TEST_TUTORIAL_MODULES_ID`)
    REFERENCES `test_tutorial_modules` (`test_tutorial_modules_id`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION;
