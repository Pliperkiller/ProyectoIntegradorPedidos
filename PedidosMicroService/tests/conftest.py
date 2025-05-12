import pytest
from src.main import create_app
from src.infrastructure.database.models.table_base_model import db

@pytest.fixture
def app():
    app = create_app()
    app.config['TESTING'] = True
    app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///:memory:'  # Base de datos en memoria
    app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

    with app.app_context():
        db.create_all()



        # Inserta productos de prueba
        from src.infrastructure.database.models.product_model import ProductModel
        db.session.query(ProductModel).delete()
        db.session.commit()
        product1 = ProductModel(id=1, name="Product 1", price=100.0)
        product2 = ProductModel(id=2, name="Product 2", price=200.0)
        db.session.add_all([product1, product2])
        db.session.commit()


        yield app

    # Limpia la base de datos después de las pruebas
    with app.app_context():
        db.session.remove()
        db.drop_all()

@pytest.fixture
def client(app):
    return app.test_client()  # Cliente de prueba para realizar solicitudes HTTP