package com.qa.utilities;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;

public class WebhookDecryptor {

    String payload = "{\"entity\":\"event\",\"account_id\":\"acc_GaADhkxSPk8t3X\",\"event\":\"payment.captured\",\"contains\":[\"payment\"],\"payload\":{\"payment\":{\"entity\":{\"id\":\"pay_JFLEVyLF4AFVNB\",\"entity\":\"payment\",\"amount\":999900,\"currency\":\"INR\",\"status\":\"captured\",\"order_id\":\"order_JFLESz3kyhv6WS\",\"invoice_id\":null,\"international\":false,\"method\":\"netbanking\",\"amount_refunded\":0,\"refund_status\":null,\"captured\":true,\"description\":\"Add Funds\",\"card_id\":null,\"bank\":\"HDFC\",\"wallet\":null,\"vpa\":null,\"email\":\"barirameshwar@gmail.com\",\"contact\":\"+917038143123\",\"notes\":{\"client_id\":\"809646\",\"address\":\"Dhani Corporate Office\"},\"fee\":null,\"tax\":null,\"error_code\":null,\"error_description\":null,\"error_source\":null,\"error_step\":null,\"error_reason\":null,\"acquirer_data\":{\"bank_transaction_id\":\"8879843\"},\"created_at\":1649088924}}},\"created_at\":1649088927}";
    String secret = "gEOQKofPz0yRBAmDY0R4BIFe";

    public WebhookDecryptor() {
    }

    public WebhookDecryptor(String payload, String secret) {
        this.payload = payload;
        this.secret = secret;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public static void main(String[] args) throws Exception{
        String payload = "{\"entity\":\"event\",\"account_id\":\"acc_GaADhkxSPk8t3X\",\"event\":\"payment.captured\",\"contains\":[\"payment\"],\"payload\":{\"payment\":{\"entity\":{\"id\":\"pay_JFLEVyLF4AFVNB\",\"entity\":\"payment\",\"amount\":999900,\"currency\":\"INR\",\"status\":\"captured\",\"order_id\":\"order_JFLESz3kyhv6WS\",\"invoice_id\":null,\"international\":false,\"method\":\"netbanking\",\"amount_refunded\":0,\"refund_status\":null,\"captured\":true,\"description\":\"Add Funds\",\"card_id\":null,\"bank\":\"HDFC\",\"wallet\":null,\"vpa\":null,\"email\":\"barirameshwar@gmail.com\",\"contact\":\"+917038143123\",\"notes\":{\"client_id\":\"809646\",\"address\":\"Dhani Corporate Office\"},\"fee\":null,\"tax\":null,\"error_code\":null,\"error_description\":null,\"error_source\":null,\"error_step\":null,\"error_reason\":null,\"acquirer_data\":{\"bank_transaction_id\":\"8879843\"},\"created_at\":1649088924}}},\"created_at\":1649088927}";
        String secret = "gEOQKofPz0yRBAmDY0R4BIFe";
        String actualSignature = getHash(payload, secret);
        System.out.println("Signature = "+actualSignature);
    }

    public static String getHash(String payload, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hash = sha256_HMAC.doFinal(payload.getBytes());
            return new String(Hex.encodeHex(hash));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public static class Hex {
        public static final Charset DEFAULT_CHARSET;
        //public static final String DEFAULT_CHARSET_NAME = "UTF-8";
        public static final char[] DIGITS_LOWER;
        public static final char[] DIGITS_UPPER;
        private final Charset charset;
        static {
            DEFAULT_CHARSET = Charset.forName("UTF-8");
            DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            DIGITS_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        }

        public static byte[] decodeHex(char[] data) throws RuntimeException {
            int len = data.length;
            if ((len & 1) != 0) {
                throw new RuntimeException("Odd number of characters.");
            } else {
                byte[] out = new byte[len >> 1];
                int i = 0;

                for(int j = 0; j < len; ++i) {
                    int f = toDigit(data[j], j) << 4;
                    ++j;
                    f |= toDigit(data[j], j);
                    ++j;
                    out[i] = (byte)(f & 255);
                }

                return out;
            }
        }

        public static char[] encodeHex(byte[] data) {
            return encodeHex(data, true);
        }

        public static char[] encodeHex(byte[] data, boolean toLowerCase) {
            return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
        }

        protected static char[] encodeHex(byte[] data, char[] toDigits) {
            int l = data.length;
            char[] out = new char[l << 1];
            int i = 0;

            for(int var5 = 0; i < l; ++i) {
                out[var5++] = toDigits[(240 & data[i]) >>> 4];
                out[var5++] = toDigits[15 & data[i]];
            }

            return out;
        }

        public static String encodeHexString(byte[] data) {
            return new String(encodeHex(data));
        }

        protected static int toDigit(char ch, int index) throws RuntimeException {
            int digit = Character.digit(ch, 16);
            if (digit == -1) {
                throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
            } else {
                return digit;
            }
        }

        public Hex() {
            this.charset = DEFAULT_CHARSET;
        }

        public Hex(Charset charset) {
            this.charset = charset;
        }

        public Hex(String charsetName) {
            this(Charset.forName(charsetName));
        }

        public byte[] decode(byte[] array) throws RuntimeException {
            return decodeHex((new String(array, this.getCharset())).toCharArray());
        }

        public Object decode(Object object) throws RuntimeException {
            try {
                char[] charArray = object instanceof String ? ((String)object).toCharArray() : (char[])((char[])object);
                return decodeHex(charArray);
            } catch (ClassCastException var3) {
                throw new RuntimeException(var3.getMessage(), var3);
            }
        }

        public byte[] encode(byte[] array) {
            return encodeHexString(array).getBytes(this.getCharset());
        }

        public Object encode(Object object) throws RuntimeException {
            try {
                byte[] byteArray = object instanceof String ? ((String)object).getBytes(this.getCharset()) : (byte[])((byte[])object);
                return encodeHex(byteArray);
            } catch (ClassCastException var3) {
                throw new RuntimeException(var3.getMessage(), var3);
            }
        }

        public Charset getCharset() {
            return this.charset;
        }

        public String getCharsetName() {
            return this.charset.name();
        }

        public String toString() {
            return super.toString() + "[charsetName=" + this.charset + "]";
        }

    }


}
