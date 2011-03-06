-- Role wise privilege data
-- application pages data 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
1, 'udValueView.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
2, 'vendorView.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
3, 'trainingItemView.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
4, 'documentView.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
5, 'modulesView.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
6, 'qustionAnsView.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
7, 'adminStory.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
8, 'manageUsersView.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
9, 'resetUserPasswordView.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
10, 'updatePaymentView.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
11, 'chapterView.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
12, 'protegeWithoutMentor.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
13, 'adminProfile.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
14, 'adminChgPassword.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
15, 'contactUsView.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
16, 'mentorDashboard.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
17, 'memberProtegeProfile.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
18, 'protegeSearch.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
19, 'leadMentorSearch.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
20, 'memberLog.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
21, 'memberStory.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
22, 'memberTutorial.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
26, 'mentorResources.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
27, 'memberAskAQuestion.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
28, 'memberDonate.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
29, 'protegeDashboard.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
30, 'protegeMentorProfile.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
31, 'mentorSearch.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
32, 'protegeViewPoint.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
33, 'protegeLog.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
34, 'protegeTutorial.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
35, 'protegeProfile.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
36, 'protegeChangePassword.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
37, 'protegeResources.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
38, 'protegeAskAQuestion.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
39, 'protegeDonate.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
23, 'mentorProfile.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
24, 'mentorChangePassword.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
25, 'mentorPayment.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
41, 'mentorStoryVote.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
40, 'protegeEvaluatMentor.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
42, 'memberTutorial.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
43, 'changePassword.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
44, 'protegeTutorial.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
46, 'tutorialModulePlayer.faces'); 
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
47, 'protegeStoryVote.faces');

INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
48, 'memberMessage.faces');
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
49, 'memberPendingMentorCertification.faces');

INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
50, 'protegeMessage.faces');
INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 
51, 'protegeEvaluatMentor.faces');

INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 52, 'protegeMessage.faces');

INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 53, 'memberMessage.faces');

INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 54, 'messageDetail.faces');

INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 55, 'protegeMessageDetail.faces');

INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 56, 'changePassword.faces');

INSERT INTO APPLICATION_PAGES ( PAGE_ID, PAGE_URL ) VALUES ( 58, 'tutorialModulePlayer.faces');


commit;
 
-- Role page access data
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
40, 17, 17, 40); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
1, 18, 18, 1); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
2, 18, 18, 2); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
3, 18, 18, 3); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
4, 18, 18, 4); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
5, 18, 18, 5); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
6, 18, 18, 6); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
7, 18, 18, 7); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
8, 18, 18, 8); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
9, 18, 18, 9); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
10, 18, 18, 10); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
11, 18, 18, 11); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
12, 18, 18, 12); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
13, 18, 18, 13); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
14, 18, 18, 14); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
15, 18, 18, 15); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
16, 15, 15, 16); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
17, 15, 15, 17); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
18, 15, 15, 18); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
19, 15, 15, 19); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
20, 15, 15, 20); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
21, 15, 15, 21); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
22, 15, 15, 22); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
23, 15, 15, 23); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
24, 15, 15, 24); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
25, 15, 15, 25); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
26, 15, 15, 26); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
27, 15, 15, 27); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
28, 15, 15, 28); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
29, 17, 17, 29); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
30, 17, 17, 30); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
31, 17, 17, 31); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
32, 17, 17, 32); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
33, 17, 17, 33); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
34, 17, 17, 34); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
35, 17, 17, 35); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
36, 17, 17, 36); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
37, 17, 17, 37); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
38, 17, 17, 38); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
39, 17, 17, 39); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
41, 15, 15, 41); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
42, 15, 15, 42); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
43, 15, 15, 43); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
44, 17, 17, 43); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
45, 15, 15, 46); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
46, 17, 17, 47); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
47, 17, 17, 46); 
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
48, 18, 18, 43);
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
49, 15, 15, 48);
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
50, 15, 15, 49);
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,
PAGE_ID ) VALUES ( 
51, 17, 17, 50);

INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,PAGE_ID ) VALUES ( 52, 17, 17, 51);

INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,PAGE_ID ) VALUES ( 53, 17, 17, 52);
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,PAGE_ID ) VALUES ( 54, 17, 17, 54);

INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,PAGE_ID ) VALUES ( 55, 15, 15, 53);
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,PAGE_ID ) VALUES ( 56, 15, 15, 54);
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,PAGE_ID ) VALUES ( 57, 15, 15, 56);

INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,PAGE_ID ) VALUES ( 58, 17, 17, 56);


INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,PAGE_ID ) VALUES ( 61, 17, 17, 58);
INSERT INTO ROLE_PAGE_ACCESS ( ROLE_PAGE_ACCESS_ID, MEMBER_ROLE_ID, MEMBER_TYPE_ID,PAGE_ID ) VALUES ( 60, 15, 15, 58);