# springai-pgvector-demo


### postgresql setup using docker

```dockerfile
docker run -it --rm --name postgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres pgvector/pgvector:0.7.4-pg16
```
