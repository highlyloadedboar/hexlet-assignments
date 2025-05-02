package exercise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Task;
import exercise.repository.TaskRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
// END
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;

    @AfterEach
    public void clearDb() {
        taskRepository.deleteAll();
    }

    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    // BEGIN
    @Test
    public void testShow() throws Exception {
        Task task = new Task();
        String title = faker.lorem().word();
        task.setTitle(title);
        String description = faker.lorem().sentence(10);
        task.setDescription(description);
        taskRepository.save(task);

        mockMvc.perform(get("/tasks/" + task.getId())).andReturn();

        Optional<Task> byId = taskRepository.findById(task.getId());
        Assertions.assertTrue(byId.isPresent());
        Assertions.assertEquals(byId.get().getId(), task.getId());
        Assertions.assertEquals(title, byId.get().getTitle());
    }


    @Test
    public void testShowNegative() throws Exception {
        Task task = new Task();
        String title = faker.lorem().word();
        task.setTitle(title);
        String description = faker.lorem().sentence(10);
        task.setDescription(description);
        taskRepository.save(task);

        mockMvc.perform(get("/tasks/" + -21212))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void testCreateNewTask() throws Exception {
        Task task = new Task();
        String title = faker.lorem().word();
        task.setTitle(title);
        String description = faker.lorem().sentence(10);
        task.setDescription(description);
        mockMvc.perform(post("/tasks").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(task)))
                .andExpect(status().isCreated()).andReturn();

        List<Task> all = taskRepository.findAll();
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(all.getFirst().getTitle(), task.getTitle());
        Assertions.assertEquals(all.getFirst().getDescription(), task.getDescription());
    }

    @Test
    public void testUpdateTask() throws Exception {
        Task task = new Task();
        String title = faker.lorem().word();
        task.setTitle(title);
        String description = faker.lorem().sentence(10);
        task.setDescription(description);
        taskRepository.save(task);

        String newTitle = faker.lorem().word();
        task.setTitle(newTitle);
        mockMvc.perform(put("/tasks/" + task.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(task))).andExpect(status().isOk()).andReturn();

        List<Task> all = taskRepository.findAll();
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals(all.getFirst().getTitle(), newTitle);
        Assertions.assertEquals(all.getFirst().getDescription(), task.getDescription());
    }

    @Test
    public void testUpdateTaskNegative() throws Exception {
        Task task = new Task();
        String title = faker.lorem().word();
        task.setTitle(title);
        String description = faker.lorem().sentence(10);
        task.setDescription(description);
        taskRepository.save(task);

        String newTitle = faker.lorem().word();
        task.setTitle(newTitle);
        mockMvc.perform(put("/tasks/" + -21212).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(task))).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void testDeleteTask() throws Exception{
        Task task = new Task();
        String title = faker.lorem().word();
        task.setTitle(title);
        String description = faker.lorem().sentence(10);
        task.setDescription(description);
        taskRepository.save(task);

        String newTitle = faker.lorem().word();
        task.setTitle(newTitle);
        mockMvc.perform(delete("/tasks/" + task.getId())).andExpect(status().isOk()).andReturn();

        List<Task> all = taskRepository.findAll();
        Assertions.assertTrue(all.isEmpty());
    }
    // END
}
