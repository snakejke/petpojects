package com.javamentor.qa.platform.webapp.controllers.rest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.qa.platform.JmApplicationTests;
import com.javamentor.qa.platform.dao.abstracts.dto.AnswerDtoDao;
import com.javamentor.qa.platform.dao.abstracts.model.AnswerDao;
import com.javamentor.qa.platform.dao.impl.dto.AnswerDtoDaoImpl;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteType;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import java.util.List;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class ResourceAnswerControllerTest extends JmApplicationTests {

    @Autowired
    private EntityManager entityManager;

    @Test
    @DataSet(value = "dataset/ResourceAnswerControllerTest/responsesToDeletion.yml",
            cleanBefore = true, cleanAfter = true)
    void checkingStatus() throws Exception {
        String token = "Bearer " + obtainAccessToken("user1@gmail.com", "admin");
        mockMvc.perform(delete("/api/user/question/{questionId}/answer/{answerId}", 100L, 100L)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = "dataset/ResourceAnswerControllerTest/responsesToDeletion.yml",
            cleanBefore = true, cleanAfter = true)
    void checkingDateCompliance() throws Exception {
        String token = "Bearer " + obtainAccessToken("user1@gmail.com", "admin");
        mockMvc.perform(delete("/api/user/question/{questionId}/answer/{answerId}", 100L, 100L)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Answer answer = entityManager.createQuery(
                        "SELECT s FROM Answer s where s.id = 100 and s.question.id = 100", Answer.class)
                .getSingleResult();
        Assertions.assertTrue(answer.getIsDeleted());
        Assertions.assertTrue(answer.getIsDeletedByModerator());
    }


    @Test
    @DataSet(value = "dataset/ResourceAnswerControllerTest/responsesToDeletion.yml",
            cleanBefore = true, cleanAfter = true)
    void aShotIntoTheVoid() throws Exception {
        String token = "Bearer " + obtainAccessToken("user1@gmail.com", "admin");
        mockMvc.perform(delete("/api/user/question/{questionId}/answer/{answerId}", 200L, 200L)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @DataSet(value = "dataset/ResourceAnswerControllerTest/downVoteAnswer.yml", cleanBefore = true, cleanAfter = true)
    void successfulDownVoteForAnswer() throws Exception {
        VoteAnswer voteAnswerBefore = entityManager.createQuery(
                        "SELECT va FROM VoteAnswer va WHERE va.id = 110", VoteAnswer.class)
                .getResultList().stream().findFirst().orElse(null);
        Reputation reputationBefore = entityManager.createQuery(
                "SELECT r FROM Reputation r WHERE r.answer.id = 101 AND r.sender.id = 100",
                Reputation.class).getSingleResult();
        Assertions.assertNull(voteAnswerBefore);
        mockMvc.perform(
                post("/api/user/question/{questionId}/answer/{answerId}/downVote", 101L, 101L)
                        .header("Authorization",
                                "Bearer " + obtainAccessToken("user1@gmail.com", "123456")));
        mockMvc.perform(
                        post("/api/user/question/{questionId}/answer/{answerId}/downVote", 101L, 101L)
                                .header("Authorization",
                                        "Bearer " + obtainAccessToken("user1@gmail.com", "123456")))
                .andExpect(status().isOk());

        VoteAnswer voteAnswerAfter = entityManager.createQuery(
                "SELECT va FROM VoteAnswer va WHERE va.user.id = 100 AND va.answer.id = 101",
                VoteAnswer.class).getSingleResult();
        Reputation reputationAfter = entityManager.createQuery(
                "SELECT r FROM Reputation r WHERE r.answer.id = 101 AND r.sender.id = 100",
                Reputation.class).getSingleResult();

        Assertions.assertNotNull(voteAnswerAfter);
        Assertions.assertNotNull(reputationAfter);

        Assertions.assertEquals(VoteType.DOWN, voteAnswerAfter.getVoteType());

        Assertions.assertNotEquals(-5, reputationBefore.getCount() - reputationAfter.getCount());
    }

    @Test
    @DataSet(value = "dataset/ResourceAnswerControllerTest/downVoteAnswer.yml", cleanBefore = true, cleanAfter = true)
    void successfulDownVoteOnAnAnswerWithAlreadyAnExistingUpvoteType() throws Exception {
        VoteAnswer voteAnswerBefore = entityManager.createQuery(
                        "SELECT va FROM VoteAnswer va WHERE va.id = 100", VoteAnswer.class)
                .getSingleResult();
        Reputation reputationBefore = entityManager.createQuery(
                "SELECT r FROM Reputation r WHERE r.answer.id = 100 AND r.sender.id = 101",
                Reputation.class).getSingleResult();
        Assertions.assertNotNull(voteAnswerBefore);
        mockMvc.perform(
                post("/api/user/question/{questionId}/answer/{answerId}/downVote", 100L, 100L)
                        .header("Authorization",
                                "Bearer " + obtainAccessToken("user2@gmail.com", "123456")));
        mockMvc.perform(
                        post("/api/user/question/{questionId}/answer/{answerId}/downVote", 100L, 100L)
                                .header("Authorization",
                                        "Bearer " + obtainAccessToken("user2@gmail.com", "123456")))
                .andExpect(status().isOk());

        VoteAnswer voteAnswerAfter = entityManager.createQuery(
                "SELECT va FROM VoteAnswer va WHERE va.user.id = 100 AND va.answer.id = 101",
                VoteAnswer.class).getSingleResult();
        Reputation reputationAfter = entityManager.createQuery(
                "SELECT r FROM Reputation r WHERE r.answer.id = 100 AND r.sender.id = 101",
                Reputation.class).getSingleResult();

        Assertions.assertNotNull(voteAnswerAfter);
        Assertions.assertNotNull(reputationAfter);

        Assertions.assertEquals(VoteType.UP, voteAnswerBefore.getVoteType());
        Assertions.assertEquals(VoteType.DOWN, voteAnswerAfter.getVoteType());

        Assertions.assertNotEquals(-5, reputationBefore.getCount() - reputationAfter.getCount());
    }

    @Test
    @DataSet(value = "dataset/ResourceAnswerControllerTest/downVoteAnswer.yml", cleanBefore = true, cleanAfter = true)
    void downVoteForNonExistenAnswer() throws Exception {
        mockMvc.perform(
                post("/api/user/question/{questionId}/answer/{answerId}/downVote", 100L, 110L)
                        .header("Authorization",
                                "Bearer " + obtainAccessToken("user1@gmail.com", "123456")));
        mockMvc.perform(
                        post("/api/user/question/{questionId}/answer/{answerId}/downVote", 100L, 110L)
                                .header("Authorization",
                                        "Bearer " + obtainAccessToken("user1@gmail.com", "123456")))
                .andExpect(status().isNotFound());
    }

    @Test
    @DataSet(value = "dataset/ResourceAnswerControllerTest/downVoteAnswer.yml", cleanBefore = true, cleanAfter = true)
    void tryingToDownVoteYourOwnAnswer() throws Exception {
        VoteAnswer voteAnswerBefore = entityManager.createQuery(
                        "SELECT va FROM VoteAnswer va WHERE va.id = 110", VoteAnswer.class)
                .getResultList().stream().findFirst().orElse(null);
        Reputation reputationBefore = entityManager.createQuery(
                "SELECT r FROM Reputation r WHERE r.answer.id = 100 AND r.sender.id = 100",
                Reputation.class).getSingleResult();
        Assertions.assertNull(voteAnswerBefore);
        mockMvc.perform(
                post("/api/user/question/{questionId}/answer/{answerId}/downVote", 101L, 100L)
                        .header("Authorization",
                                "Bearer " + obtainAccessToken("user1@gmail.com", "123456")));
        mockMvc.perform(
                        post("/api/user/question/{questionId}/answer/{answerId}/downVote", 101L, 100L)
                                .header("Authorization",
                                        "Bearer " + obtainAccessToken("user1@gmail.com", "123456")))
                .andExpect(status().isNotFound());

        VoteAnswer voteAnswerAfter = entityManager.createQuery(
                        "SELECT va FROM VoteAnswer va WHERE va.user.id = 100 AND va.answer.id = 100",
                        VoteAnswer.class)
                .getResultList().stream().findFirst().orElse(null);
        Reputation reputationAfter = entityManager.createQuery(
                "SELECT r FROM Reputation r WHERE r.answer.id = 100 AND r.sender.id = 100",
                Reputation.class).getSingleResult();

        Assertions.assertNull(voteAnswerAfter);

        Assertions.assertEquals(reputationBefore.getCount(), reputationAfter.getCount());
    }

    @Test //тест на голосование за несуществующий ответ
    @DataSet(value = "dataset/ResourceAnswerControllerTest/UpVoteAnswer.yml")
    void answerNotFoundForVoteUp() throws Exception {
        mockMvc.perform(post("/api/user/question/{questionId}/answer/{answerId}/upVote", 100L, 150L)
                        .header("Authorization",
                                "Bearer " + obtainAccessToken("rudova_l@list.ru", "123456")))
                .andExpect(status().isNotFound());
    }

    @Test  //успешное голосование Up
    @DataSet(value = "dataset/ResourceAnswerControllerTest/UpVoteAnswer.yml")
    void successUpVoteAnswer() throws Exception {

        Reputation reputationBefore = entityManager.createQuery(
                "SELECT r FROM Reputation r WHERE r.answer.id = 100 AND r.sender.id = 100",
                Reputation.class).getSingleResult();

        mockMvc.perform(post("/api/user/question/{questionId}/answer/{answerId}/upVote", 100L, 100L)
                        .header("Authorization",
                                "Bearer " + obtainAccessToken("rudova_l@list.ru", "123456")))
                .andExpect(status().isOk());

        VoteAnswer voteAnswer = entityManager.createQuery(
                "SELECT va FROM VoteAnswer va WHERE va.user.id = 100 AND va.answer.id = 100",
                VoteAnswer.class).getSingleResult();
        Reputation reputationAfter = entityManager.createQuery(
                "SELECT r FROM Reputation r WHERE r.answer.id = 100 AND r.sender.id = 100",
                Reputation.class).getSingleResult();

        Assertions.assertNotNull(voteAnswer);
        Assertions.assertNotNull(reputationAfter);

        Assertions.assertEquals(VoteType.UP, voteAnswer.getVoteType());
        Assertions.assertEquals(-10, reputationBefore.getCount() - reputationAfter.getCount());


    }

    @Test // успешная замена голоса с DOWN на UP
    @DataSet(value = "dataset/ResourceAnswerControllerTest/UpVoteAnswer.yml")
    void successOppositeVoteFromDownToUp() throws Exception {
        VoteAnswer voteAnswerBefore = entityManager.createQuery(
                "SELECT va FROM VoteAnswer va WHERE va.id = 100 AND va.answer.id = 100",
                VoteAnswer.class).getSingleResult();
        Reputation reputationBefore = entityManager.createQuery(
                "SELECT r FROM Reputation r WHERE r.answer.id = 101 AND r.sender.id = 100",
                Reputation.class).getSingleResult();
        Assertions.assertNotNull(voteAnswerBefore);
        mockMvc.perform(post("/api/user/question/{questionId}/answer/{answerId}/upVote", 100L, 101L)
                        .header("Authorization",
                                "Bearer " + obtainAccessToken("rudova_l@list.ru", "123456")))
                .andExpect(status().isOk());

        VoteAnswer voteAnswer = entityManager.createQuery(
                "SELECT va FROM VoteAnswer va WHERE va.user.id = 100 AND va.answer.id = 101",
                VoteAnswer.class).getSingleResult();
        Reputation reputationAfter = entityManager.createQuery(
                "SELECT r FROM Reputation r WHERE r.answer.id = 101 AND r.sender.id = 100",
                Reputation.class).getSingleResult();

        Assertions.assertNotNull(voteAnswer);
        Assertions.assertNotNull(reputationAfter);

        Assertions.assertEquals(VoteType.DOWN, voteAnswerBefore.getVoteType());
        Assertions.assertEquals(VoteType.UP, voteAnswer.getVoteType());
        Assertions.assertEquals(-10, reputationBefore.getCount() - reputationAfter.getCount());


    }

    @Test //голосование за свой же ответ (запрещено)
    @DataSet(value = "dataset/ResourceAnswerControllerTest/UpVoteAnswer.yml")
    void tryingToUpVoteYourOwnAnswer() throws Exception {

        Reputation reputationBefore = entityManager.createQuery(
                        "SELECT r FROM Reputation r WHERE r.answer.id = 100 AND r.sender.id = 101",
                        Reputation.class)
                .getResultList().stream().findFirst().orElse(null);

        mockMvc.perform(post("/api/user/question/{questionId}/answer/{answerId}/upVote", 100L, 100L)
                        .header("Authorization", "Bearer " + obtainAccessToken("user1@list.ru", "123456")))
                .andExpect(status().isNotFound());

        VoteAnswer voteAnswer = entityManager.createQuery(
                        "SELECT va FROM VoteAnswer va WHERE va.user.id = 101 AND va.answer.id = 100",
                        VoteAnswer.class)
                .getResultList().stream().findFirst().orElse(null);

        Reputation reputationAfter = entityManager.createQuery(
                "SELECT r FROM Reputation r WHERE r.answer.id = 100 AND r.sender.id = 101",
                Reputation.class).getSingleResult();

        Assertions.assertNull(voteAnswer);

        Assertions.assertEquals(reputationBefore.getCount(), reputationAfter.getCount());
    }

    @Test
    @DataSet(value = "dataset/ResourceAnswerControllerTest/getAllAnswers.yml", cleanBefore = true, cleanAfter = true)
    void testGetAllAnswers() throws Exception {
        int idWithAnswers = 101;
        int idWithoutAnswers = 102;
        int idNonExist = 106;
        String URL = "/api/user/question/{questionId}/answer";
        String username = "user1@gmail.com";
        String password = "123456";

        mockMvc.perform(get(URL, idWithAnswers).header("Authorization",
                        "Bearer " + obtainAccessToken(username, password)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].id").value(101))
                .andExpect(jsonPath("$.[0].userId").value(103))
                .andExpect(jsonPath("$.[0].questionId").value(101))
                .andExpect(jsonPath("$.[0].body").value("answer_from_admin"))
                .andExpect(jsonPath("$.[0].persistDate").value("2024-02-26T18:06:11.524439"))
                .andExpect(jsonPath("$.[0].isHelpful").value("false"))
                .andExpect(jsonPath("$.[0].dateAccept").value("2024-02-26T18:06:11.524439"))
                .andExpect(jsonPath("$.[0].countValuable").value(-2))
                .andExpect(jsonPath("$.[0].countUserReputation").value(0))
                .andExpect(jsonPath("$.[0].image").value("link_image_admin"))
                .andExpect(jsonPath("$.[0].nickname").value("admin"))
                .andExpect(jsonPath("$.[0].countVote").value(2))
                .andExpect(jsonPath("$.[0].voteType").value("DOWN"))
                // Второй ответ
                .andExpect(jsonPath("$.[1].id").value(102))
                .andExpect(jsonPath("$.[1].userId").value(110))
                .andExpect(jsonPath("$.[1].questionId").value(101))
                .andExpect(jsonPath("$.[1].body").value("answer_from_User110"))
                .andExpect(jsonPath("$.[1].persistDate").value("2024-02-27T18:06:11.524439"))
                .andExpect(jsonPath("$.[1].isHelpful").value("false"))
                .andExpect(jsonPath("$.[1].dateAccept").value("2024-02-27T18:06:11.524439"))
                .andExpect(jsonPath("$.[1].countValuable").value(0))
                .andExpect(jsonPath("$.[1].countUserReputation").value(10))
                .andExpect(jsonPath("$.[1].image").value("link_image_user10"))
                .andExpect(jsonPath("$.[1].nickname").value("user10"))
                .andExpect(jsonPath("$.[1].countVote").value(0));

        mockMvc.perform(get(URL, idWithoutAnswers).header("Authorization",
                        "Bearer " + obtainAccessToken(username, password)))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get(URL, idNonExist).header("Authorization",
                        "Bearer " + obtainAccessToken(username, password)))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @DataSet(value = "dataset/ResourceAnswerControllerTest/getAllAnswers.yml", cleanBefore = true, cleanAfter = true)
    void testGetAnswersAuth() throws Exception {
        int questionId = 101;
        String URL = "/api/user/question/{questionId}/answer";

        // 401 Unauthorized
        mockMvc.perform(get(URL, questionId))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        // 401 Unauthorized
        String invalidToken = "invalid_token";
        mockMvc.perform(get(URL, questionId).header("Authorization", "Bearer " + invalidToken))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        // 403 Forbidden
        String nonExistentUserToken = "Bearer " + obtainAccessToken("admin@gmail.com", "123456");
        mockMvc.perform(get(URL, questionId).header("Authorization", nonExistentUserToken))
                .andExpect(status().isForbidden());

        // 200 OK
        String user2Token = "Bearer " + obtainAccessToken("user2@gmail.com", "123456");
        mockMvc.perform(get(URL, questionId).header("Authorization", user2Token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Autowired
    private AnswerDtoDao answerDtoDao;

    @Test
    @DataSet(value = "dataset/ResourceAnswerControllerTest/getAllAnswers.yml", cleanBefore = true, cleanAfter = true)
    void shouldAggregateAnswersCorrectly() {

        List<AnswerDto> resultList = answerDtoDao.getAllAnswersDtoByQuestionId(101L, 102L);

        Assertions.assertFalse(resultList.isEmpty());

        AnswerDto result = resultList.get(0);
        Assertions.assertEquals(101L, result.getId());
        Assertions.assertEquals(103L, result.getUserId());
        Assertions.assertEquals(101L, result.getQuestionId());
        Assertions.assertEquals(-2, result.getCountValuable());
        Assertions.assertEquals(0, result.getCountUserReputation());
        Assertions.assertEquals(2, result.getCountVote());
    }

}
