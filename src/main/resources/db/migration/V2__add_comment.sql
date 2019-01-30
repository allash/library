CREATE TABLE "comment" (
     "id" SERIAL PRIMARY KEY,
     "text" TEXT NOT NULL,
     "book_id" BIGINT NOT NULL,
     CONSTRAINT "book_comment_fk" FOREIGN KEY ("book_id") REFERENCES "book" ("id")
);