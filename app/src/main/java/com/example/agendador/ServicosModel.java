package com.example.agendador;

public class ServicosModel {


        public String data;
        public String hora;
        public String titulo;
        public String status;

        public ServicosModel(String data, String hora, String titulo, String status) {
            this.data = data;
            this.hora = hora;
            this.titulo = titulo;
            this.status = status;
        }

        public String getData() {

            return data;
        }

        public void setData(String data) {

            this.data = data;
        }

        public String getHora() {

            return hora;
        }

        public void setHora(String hora)
        {
            this.hora = hora;
        }

        public String getTitulo() {

            return titulo;
        }

        public void setTitulo(String titulo)
        {
            this.titulo = titulo;
        }

        public String getStatus() {

            return status;
        }

        public void setStatus(String status)
        {
            this.status = status;
        }

}
