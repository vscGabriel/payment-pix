db = db.getSiblingDB("pix")
db.createUser(
    {
        user: "vscgab",
        pwd: "vscgab",
        roles: [
            {
                role: "readWrite",
                db: "pix"
            }
        ]
    }
);
db.createCollection("transacao_pix");

print('DB and users created successfully!');
