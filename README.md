# e-commerce-micro
A JAVA based micro grpc service, gateway and gRPC microservices for accounts, products, orders, and recommendations.

## For development
setup the environment variable in dev 
this is to make sure your dev environment is the same as production.

`
run "echo 'export DB_USERNAME=postgres' >> ~/.zshrc"
`

`
run "echo 'export DB_PASSWORD=password' >> ~/.zshrc"
`

`
run Debug [Service Name]
`

## For production deployment:
In GitHub, go to your repository settings
Navigate to "Secrets and variables" → "Actions"

Add the following secrets:

- DB_URL: Your database URL

- DB_USERNAME: Database username

- DB_PASSWORD: Database password

- DOCKERHUB_USERNAME: Your Docker Hub 
username

- DOCKERHUB_TOKEN: Your Docker Hub access 
token

The CI/CD pipeline creates the properties file using production secrets
The application is containerized
The Docker image is pushed to Docker Hub
You can then deploy the container to your production environment