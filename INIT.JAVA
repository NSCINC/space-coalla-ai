/*
 * Copyright (c) NSC Inc.
 * All rights reserved.
 * 
 * This software may be used and distributed according to the terms
 * of the NSC Inc License Agreement.
 */

 package com.nscinc.project;

 public class NSC_ABC {
     
     private String modelName;
     private String version;
 
     // Construtor
     public NSC_ABC(String modelName, String version) {
         this.modelName = modelName;
         this.version = version;
     }
 
     // Método de geração
     public String generate(String input) {
         // Lógica para geração com base no modelo NSC_ABC
         return "Generated output based on: " + input;
     }
 
     // Exemplo de diálogo baseado no modelo
     public void dialog() {
         System.out.println("Dialog with NSC_ABC model " + modelName + " version " + version);
     }
 
     // Getters e Setters
     public String getModelName() {
         return modelName;
     }
 
     public void setModelName(String modelName) {
         this.modelName = modelName;
     }
 
     public String getVersion() {
         return version;
     }
 
     public void setVersion(String version) {
         this.version = version;
     }
 
     public static void main(String[] args) {
         NSC_ABC nscModel = new NSC_ABC("NSC-ABC", "1.0");
         nscModel.dialog();
         String output = nscModel.generate("Teste de entrada");
         System.out.println(output);
     }
 }
 