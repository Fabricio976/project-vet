package com.project.services.details;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import freemarker.template.Configuration;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String remetente;

    /**
     * envia um email de texto simples para mudança da senha
     *
     * @param destinatario endereço de email do destinatário
     * @param titulo       assunto do email
     * @param mensagem     corpo da mensagem
     * @return mensagem indicando o sucesso ou falha do envio do email
     */
    public String enviarEmailTexto(String destinatario, String titulo, String mensagem) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo(destinatario);
            simpleMailMessage.setSubject(titulo);
            simpleMailMessage.setText(mensagem);
            javaMailSender.send(simpleMailMessage);
            return "Email enviado";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Erro ao enviar o email";
        }
    }

    /**
     * Envia um email com um template
     *
     * @param destinatario  endereço de email do destinatário
     * @param titulo        assunto do email
     * @param propriedades  propriedades a serem usadas no template do email
     */
    public void enviarEmailTemplate(String destinatario, String titulo, Map<String, Object> propriedades) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(titulo);
            mimeMessageHelper.setFrom(remetente);
            mimeMessageHelper.setTo(destinatario);
            mimeMessageHelper.setText(getConteudoTemplate(propriedades), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * gera o conteúdo do email a partir de um template
     *
     * @param model  dados a serem incluídos no template
     * @return  conteúdo do email gerado a partir do template
     */
    public String getConteudoTemplate(Map<String, Object> model) {
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("recuperacao-codigo.ftl"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}

