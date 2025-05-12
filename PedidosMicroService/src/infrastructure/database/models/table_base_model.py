from flask_sqlalchemy import SQLAlchemy
from sqlalchemy.orm import DeclarativeBase
from sqlalchemy import MetaData
from datetime import datetime, timezone

class Base(DeclarativeBase):
    metadata = MetaData(naming_convention={
        "ix": 'ix_%(column_0_label)s',
        "uq": "uq_%(table_name)s_%(column_0_name)s",
        "ck": "ck_%(table_name)s_%(constraint_name)s",
        "fk": "fk_%(table_name)s_%(column_0_name)s_%(referred_table_name)s",
        "pk": "pk_%(table_name)s"
    })


db = SQLAlchemy(model_class=Base)

class DbModel(db.Model):
    __abstract__ = True


    created_at = db.Column(db.DateTime, default=lambda: datetime.now(tz=timezone.utc), nullable=False)

    def __repr__(self):
        return f"<Model {self.id}>"