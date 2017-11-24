package com.pactera.activiti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by pactera on 2017/11/24.
 */
@Controller
@RequestMapping("/process")
public class ModelController {
   private Logger logger = LoggerFactory.getLogger(ModelController.class);
   @Autowired
   private RepositoryService repositoryService;


   /**
    * 流程实例列表
    * @return
    */
   @RequestMapping("/modelList")
   public ModelAndView modelList(){
      ModelAndView mv = new ModelAndView();
      List<Model> list = repositoryService.createModelQuery().orderByLastUpdateTime().asc().list();
      mv.addObject("list",list);
      mv.setViewName("activiti/modelList");
      return mv;
   }
   /**
    * 进入模型创建页面
    */
   @RequestMapping("/addModel")
   public String addModelPage(){
      return "activiti/addModel";
   }
   /**
    * 创建模型
    * @param name	模型名称
    * @param key	模型key
    * @param description	描述
    * @param category 流程模型分类
    */
   @RequestMapping("/create")
   private ModelAndView create(@RequestParam("name") String name, @RequestParam("key") String key,
                       @RequestParam("description") String description, @RequestParam("category") String category
                       ) {
      try {
         ObjectMapper objectMapper = new ObjectMapper();
         ObjectNode editorNode = objectMapper.createObjectNode();
         editorNode.put("id", "canvas");
         editorNode.put("resourceId", "canvas");
         ObjectNode stencilSetNode = objectMapper.createObjectNode();
         stencilSetNode.put("url", "stencilsets/bpmn2.0/bpmn2.0.json");
         stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
         editorNode.put("stencilset", stencilSetNode);
         Model modelData = repositoryService.newModel();

         ObjectNode modelObjectNode = objectMapper.createObjectNode();
         modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
         modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
         modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, StringUtils.defaultString(description));
         modelObjectNode.put("category", StringUtils.defaultString(category));//流程模型分类(自定义)
         modelData.setMetaInfo(modelObjectNode.toString());
         modelData.setName(name);
         modelData.setKey(StringUtils.defaultString(key));
         modelData.setCategory(category);

         repositoryService.saveModel(modelData);
         repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
         return new ModelAndView("redirect:/process/editorActiviti?modelId="+modelData.getId());
      } catch (Exception e) {
         logger.error("创建模型失败：", e);
         return null;
      }
   }

   /**
    * 编辑模型
    * @param modelId
    * @return
    */
   @RequestMapping("/editorActiviti")
   public ModelAndView editorActiviti(String modelId) {
      ModelAndView mv = new ModelAndView();
      mv.addObject("modelId", modelId);
      mv.setViewName("activiti/editor");
      return mv;
   }

   /**
    * 部署模型
    * @param modelId
    * @return
    */
   @ResponseBody
   @RequestMapping("/deployModel")
   public String deployModel(String modelId){
      try {
         Model model = repositoryService.getModel(modelId);
         ObjectNode node = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelId));

         byte[] bpmnBytes = null;
         BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(node);
         bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
         String processName = model.getName() + ".bpmn20.xml";
         repositoryService.createDeployment().category(model.getCategory())
               .name(model.getName()).addString(processName, new String(bpmnBytes,"UTF-8")).deploy();
         return "true";
      }catch (Exception e){
         logger.error("流程部署失败");
         return "false";
      }
   }
}
