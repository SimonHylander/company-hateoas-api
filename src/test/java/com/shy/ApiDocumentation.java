package com.shy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shy.company.Company;
import com.shy.company.CompanyRepository;
import com.shy.company.CompanyResource;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;


import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = ApiApplication.class)
public class ApiDocumentation {

    @MockBean
    private DatabaseInitializer initializer; // Disable database init

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .build();
    }

    @Test
    public void root() throws Exception {
        this.mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andDo(document(
                    "root",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                links(
                        linkWithRel("self").description("The entry point into the service"),
                        linkWithRel("companies").description("The company resource")
                ),
                responseFields(subsectionWithPath("_links").description("<<resources-root-links, Links>> to other resources")),
                responseHeaders(headerWithName("Content-Type").description("`application/hal+json`"))
            ));
    }

    @Test
    public void companyCreate() throws Exception {
        Map<String, Object> company = new HashMap<>();
        company.put("name", "Company");
        company.put("description", "Description..");
        company.put("organisationNumber", "123456-78910");

        this.mockMvc.perform(
            post("/companies")
                .contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(company))
        )
        .andExpect(status().isCreated())
        .andDo(document("company-create",
            requestFields(
                fieldWithPath("name").description("The name of the company"),
                fieldWithPath("description").description("The description of the company"),
                fieldWithPath("organisationNumber").description("The organisation number of the company")
            )
        ));
    }

    @Test
    public void companyListSuccessfully() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/companies"))
            .andExpect(status().isOk())
            .andDo(document("company-list",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                relaxedResponseFields(
                    fieldWithPath("_embedded.companies").description("An array of <<resources-company, Company resources>>"),
                    fieldWithPath("_links").description("<<resources-company-links, Links>> to other resources")
                ),
                responseHeaders(headerWithName("Content-Type").description("`application/hal+json`"))
            ));
    }

    @Test
    public void companyGet() throws Exception {
        Map<String, Object> company = new HashMap<>();
        company.put("name", "Company");
        company.put("description", "Description..");
        company.put("organisationNumber", "123456-78910");

        String companyLocation = this.mockMvc
            .perform(post("/companies")
                .contentType(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(company)))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getHeader("Location");

        Assert.notNull(companyLocation, "Location is null");

        this.mockMvc
            .perform(get(companyLocation))
            .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(company.get("name"))))
                .andExpect(jsonPath("description", is(company.get("description"))))
                .andExpect(jsonPath("organisationNumber", is(company.get("organisationNumber"))))
            .andDo(document("company-get",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                links(halLinks(), linkWithRel("self").description("This <<resources-company-get, company>>")),
                relaxedResponseFields(
                    fieldWithPath("uniqueId").description("The uniqueId of the company"),
                    fieldWithPath("organisationNumber").description("The organisation number of the company"),
                    fieldWithPath("name").description("The name of the company"),
                    fieldWithPath("description").description("The description of the company"),
                    fieldWithPath("_links").description("<<resources-company-links, Links>> to other resources")
                ),
                responseHeaders(headerWithName("Content-Type").description("`application/hal+json`"))
            ));
    }
}