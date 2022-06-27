package com.s4ulo.whatsappclone.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validaPermisoes (int requestCode, Activity activity, String [] permissoes ){
        if(Build.VERSION.SDK_INT>=23){

            List<String> listaPermissoes = new ArrayList<String>();


            // Verificar as permissoes passadas
            for (String permmissao : permissoes){

                Boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permmissao) == PackageManager.PERMISSION_GRANTED;
                if (!validaPermissao) listaPermissoes.add(permmissao);
            }

            /* Caso lista for vazia nao e necessario solicitar permissao */
            if (listaPermissoes.isEmpty()) return true;

            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            /* Solicita permissao */

            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);


        }

        return true;
    }


}
