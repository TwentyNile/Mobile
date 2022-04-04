const express = require('express');
const mongodb = require('mongodb');
const ObjectID = mongodb.ObjectId;
const crypto = require('crypto');
const bodyParser = require('body-parser');
const multer = require('multer');


var getRandomString = length => {
    return crypto.randomBytes(Math.ceil(length/2)).toString('hex').slice(0,length);
}

var sha512 = (password,salt) => {
    var hash = crypto.createHmac('sha512',salt);
    hash.update(password);
    var value = hash.digest('hex');
    return {
        salt:salt,
        passwordHash:value
    };
};

function saltHashPassword(userPassword){
    var salt = getRandomString(16)
    var passwordData = sha512(userPassword,salt);
    return passwordData;
}

function checkHashPassword(userPassword,salt){
    var passwordData = sha512(userPassword,salt);
    return passwordData;
}

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));

const MongoClient = mongodb.MongoClient;

const url = 'mongodb://0.0.0.0:27017';

MongoClient.connect(url,{useNewUrlParser:true}, (err,client) => {
    if(err){
        console.log("Failed to connect to MongoDb server",err);
    }
    else{   
        //Register
        app.post('/register', (req,res,next) =>{
            var post_data = req.body;

            var plaint_password = post_data.password;
            var hash_data = saltHashPassword(plaint_password);

            var password = hash_data.passwordHash;
            var salt = hash_data.salt;

            var name = post_data.name;
            var email = post_data.email;

            var insertJson = {
                'name': name,
                'email': email,
                'password': password,
                'salt':salt
            };
            
            var db = client.db('panjs');

            db.collection('users')
                .find({'email':email}).count((err,number)=>{
                    if(number != 0){
                        res.json('Email already exist');
                        console.log('Email already exist');
                    }
                    else{
                        db.collection('users')
                            .insertOne(insertJson, (err,respond) => {
                                res.json('Registration Successfully');
                                console.log('Registration Successfully');
                            })
                    }

                })
        })

        app.post('/login', (req,res,next) =>{
            var i;
            var post_data = req.body;
            
            var email = post_data.email;
            var userPassword = post_data.password;
            
            var db = client.db('panjs');

            db.collection('users')
                .find({'email':email}).count((err,number)=>{
                    if(number == 0){
                        res.json('Email not exist');
                        console.log('Email not exist');
                    }
                    else{
                        db.collection('users')
                            .findOne({'email':email}, (err,user) => {
                                var salt = user.salt;
                                var hash_password = checkHashPassword(userPassword,salt).passwordHash;
                                var encrypt_password = user.password;
                                if(hash_password == encrypt_password){
                                    res.json('Login Success');
                                    console.log('Login Success');
                                    var i = 1 ;
                                    return i ;
                                }
                                else{
                                    res.json('Wrong Password');
                                    console.log('Wrong Password');
                                    var i = 0 ;
                                    return i ;
                                }
                            })

                    }
                    
                })
        })
        app.get('/detail', (req,res,next) => {
            var db = client.db('panjs');
            db.collection('sedan').find({}).toArray( (err,data) => {
            if(err) throw err;
                res.send(data);})
        })
        app.get('/detail1', (req,res,next) => {
            var db = client.db('panjs');
            db.collection('coupe').find({}).toArray( (err,data) => {
            if(err) throw err;
                res.send(data);})
        })
        app.get('/fluke', (req,res,next) => {
            var db = client.db('panjs');
            db.collection('sport').find({}).toArray( (err,data) => {
            if(err) throw err;
                res.send(data);})
        })

       
        app.listen(3000, () => {
            console.log("Connected to MongoDB")
        })
    }
})



