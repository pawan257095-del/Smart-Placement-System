package com.smartplacement.service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {

    private final String senderEmail;
    private final String appPassword;

    public EmailService() {

        senderEmail =
                System.getenv("SMART_PLACEMENT_EMAIL");

        appPassword =
                System.getenv("SMART_PLACEMENT_EMAIL_PASSWORD");

        if (senderEmail == null ||
                senderEmail.isBlank()) {

            throw new IllegalStateException(
                    "SMART_PLACEMENT_EMAIL environment variable is not configured."
            );
        }

        if (appPassword == null ||
                appPassword.isBlank()) {

            throw new IllegalStateException(
                    "SMART_PLACEMENT_EMAIL_PASSWORD environment variable is not configured."
            );
        }
    }

    public boolean sendOtpEmail(
            String recipientEmail,
            String recipientName,
            String otp) {

        Properties properties =
                new Properties();

        properties.put(
                "mail.smtp.host",
                "smtp.gmail.com"
        );

        properties.put(
                "mail.smtp.port",
                "587"
        );

        properties.put(
                "mail.smtp.auth",
                "true"
        );

        properties.put(
                "mail.smtp.starttls.enable",
                "true"
        );

        properties.put(
                "mail.smtp.starttls.required",
                "true"
        );

        Session session =
                Session.getInstance(
                        properties,
                        new Authenticator() {

                            @Override
                            protected PasswordAuthentication
                            getPasswordAuthentication() {

                                return new PasswordAuthentication(
                                        senderEmail,
                                        appPassword
                                );
                            }
                        }
                );

        try {

            Message message =
                    new MimeMessage(session);

            message.setFrom(
                    new InternetAddress(
                            senderEmail,
                            "Smart Placement System"
                    )
            );

            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(
                            recipientEmail
                    )
            );

            message.setSubject(
                    "Smart Placement System - Password Reset OTP"
            );

            String safeName =
                    recipientName == null ||
                    recipientName.isBlank()
                            ? "User"
                            : recipientName;

            String body =
                    "Hello " + safeName + ",\n\n"
                    + "We received a request to reset your "
                    + "Smart Placement System password.\n\n"
                    + "Your verification OTP is:\n\n"
                    + otp + "\n\n"
                    + "Enter this OTP in the application to "
                    + "continue with password reset.\n\n"
                    + "If you did not request a password reset, "
                    + "please ignore this email.\n\n"
                    + "Regards,\n"
                    + "Smart Placement System";

            message.setText(body);

            Transport.send(message);

            return true;

        } catch (MessagingException e) {

            System.out.println();
            System.out.println(
                    "Unable to send OTP email."
            );

            System.out.println(
                    "Email error: "
                            + e.getMessage()
            );

            return false;

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "Unexpected error while sending email."
            );

            System.out.println(
                    "Error: "
                            + e.getMessage()
            );

            return false;
        }
    }
}