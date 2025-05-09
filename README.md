# soat-techchallenge
Comando para criar imagem do database no docker:

    - docker build -t minha-imagem-postgres:tag1 -f Dockerfile .  
    - docker run -d -p 5432:5432 --name meu-postgres minha-imagem-postgres:tag1