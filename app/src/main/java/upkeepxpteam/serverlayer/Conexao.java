package upkeepxpteam.serverlayer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Felipe on 26/11/2017.
 */

public class Conexao {

    public static String postDados(String urlUsuario, String parametroUsuario){
        URL url;
        HttpURLConnection connection = null;

        try{
            url = new URL(urlUsuario);
            connection = (HttpURLConnection) url.openConnection();
            //configurar conexão
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length","" + Integer.toString(parametroUsuario.getBytes().length));
            connection.setRequestProperty("Content-Language","pt-BR");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //criar saida
            /*
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(parametroUsuario);
            dataOutputStream.flush();
            dataOutputStream.close();
            */
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
            outputStreamWriter.write(parametroUsuario);
            outputStreamWriter.flush();

            //obter informação
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String linha;
            StringBuffer resposta = new StringBuffer();

            while((linha=bufferedReader.readLine()) != null){
                resposta.append(linha);
                resposta.append('\r');
            }

            bufferedReader.close();

            return resposta.toString();

        }catch (Exception err){

            return  null;
        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }
}
