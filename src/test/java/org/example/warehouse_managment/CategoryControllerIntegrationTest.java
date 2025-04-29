package org.example.warehouse_managment;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.warehouse_managment.db_dto.CategoryDTO;
import org.example.warehouse_managment.model.Category;
import org.example.warehouse_managment.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test") //H2 база
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        categoryRepository.deleteAll();
    }

    @Test
    void testGetAllCategories() throws Exception {
        categoryRepository.save(new Category(1, "Food"));
        categoryRepository.save(new Category(2, "Tools"));

        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void testAddCategorySuccess() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO("Books");

        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Books")));
    }

    @Test
    void testGetCategoryById() throws Exception {
        Category category = categoryRepository.save(new Category(1, "Electronics"));

        mockMvc.perform(get("/category/" + category.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Electronics")));
    }

    @Test
    void testUpdateCategory() throws Exception {
        Category category = categoryRepository.save(new Category(1, "OldName"));
        category.setName("NewName");

        mockMvc.perform(put("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("NewName")));
    }

    @Test
    void testDeleteCategory() throws Exception {
        Category category = categoryRepository.save(new Category(1, "ToDelete"));

        mockMvc.perform(delete("/category/" + category.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/category/" + category.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddCategoryWithEmptyName() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO(""); // порожнє ім’я

        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testGetCategoryByNonExistentId() throws Exception {
        mockMvc.perform(get("/category/999"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testUpdateNonExistentCategory() throws Exception {
        Category fakeCategory = new Category(999, "GhostCategory");

        mockMvc.perform(put("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fakeCategory)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteNonExistentCategory() throws Exception {
        mockMvc.perform(delete("/category/999"))
                .andExpect(status().isNotFound());
    }

}
