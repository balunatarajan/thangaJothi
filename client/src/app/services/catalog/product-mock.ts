//import {Product} from '../../models/catalog/product';

/*export var ProdcutsMock: Array<Product>=[
    {
      "id": 101,
      "Name": "She Swiped Right into My Heart",
      "Category": "Books",
      "Description": "Love, relationship and friendships are not defined for our generation. The boundaries between them are often blurred and the result is often full of confusion and tears. 'She swiped right into my heart’ by Sudeep Nagarkar",
      "Price": 87,
      "Rating": 4.7,
      "Image": "Book1.jpg"
    },
    {
      "id": 102,
      "Name": "The PCOD - Thyroid Book",
      "Category": "Books",
      "Description": "PCOD or Poly Cystic Ovarian Disease has become a common phenomenon in today's times. Writer and nutritional expert Rujita Diwekar, in her book 'The PCOD – Thyroid Book’ claims that PCOD has become a common occurrence ",
      "Price": 80,
      "Rating": 4,
      "Image": "Book2.jpg"
    },
    {
      "id": 103,
      "Name": "Zero to One: Note on Start Ups, or How to Build the Future",
      "Category": "Books",
      "Description": "The book Zero To One is about nurturing the next big idea to build a valuable global company. If you are only following in the steps known entrepreneurs, then the authors believe that you are headed nowhere.",
      "Price": 249,
      "Rating": 4.5,
      "Image": "Book3.jpg"
    },
    {
      "id": 104,
      "Name": "Rich Dad Poor Dad",
      "Category": "Books",
      "Description": "Written by Robert Kiyosaki and Sharon Lechter in 1997, Rich Dad Poor Dad is based mostly on Kiyosaki's young days spent in Hawaii. Enriched by Kiyosaki’s personal experience and the teachings he received from his rich dad and poor dad, the book highlights different attitudes towards money, work and life",
      "Price": 109.52,
      "Rating": 4.9,
      "Image": "Book4.jpg"
    },
    {
      "id": 105,
      "Name": "How to Win Friends and Influence People",
      "Category": "Books",
      "Description": "'How to win friends and influence people’ is a self-help book which is the pioneer of this genre. Written by Dale Carnegie and published in 1936, it has sold over 30 million copies. It has been edited and re-printed several times",
      "Price": 99,
      "Rating": 4.4,
      "Image": "Book5.jpg"
    },
    {
      "id": 106,
      "Name": "Wings of Fire: An Autobiography",
      "Category": "Books",
      "Description": "Every common man who by his sheer grit and hard work achieves success should share his story with the rest for they may find inspiration and strength to go on, in his story. The 'Wings of Fire' is one such autobiography by visionary scientist Dr. APJ Abdul Kalam, who from very humble beginnings rose to be the President of India.",
      "Price": 105,
      "Rating": 5,
      "Image": "Book6.jpg"
    },
    {
      "id": 107,
      "Name": "Moto G Plus, 4th Gen (White, 32 GB)",
      "Category": "Mobiles",
      "Description": "Great pictures make all the difference. That’s why there’s the new Moto G Plus, 4th Gen. It gives you a 16 MP camera with laser focus and a whole lot more, so you can say goodbye to blurry photos and missed shots. Instantly unlock your phone using your unique fingerprint as a passcode. Get up to 6 hours of power in just 15 minutes of charging",
      "Price": 14999,
      "Rating": 4,
      "Image": "Moto1.jpg"
    },
    {
      "id": 108,
      "Name": "Moto G Plus, 4th Gen (Black, 32 GB)",
      "Category": "Mobiles",
      "Description": "Great pictures make all the difference. That’s why there’s the new Moto G Plus, 4th Gen. It gives you a 16 MP camera with laser focus and a whole lot more, so you can say goodbye to blurry photos and missed shots. Instantly unlock your phone using your unique fingerprint as a passcode. Get up to 6 hours of power in just 15 minutes of charging",
      "Price": 14999,
      "Rating": 4,
      "Image": "Moto2.jpg"
    },
    {
      "id": 109,
      "Name": "Moto G, 4th Gen (Black, 16GB)",
      "Category": "Mobiles",
      "Description": "",
      "Price": 12499,
      "Rating": 4,
      "Image": "Moto3.jpg"
    },
    {
      "id": 110,
      "Name": "Moto G Turbo (Black, 16GB)",
      "Category": "Mobiles",
      "Description": "",
      "Price": 12200,
      "Rating": 3.6,
      "Image": "Moto4.jpg"
    },
    {
      "id": 111,
      "Name": "Moto X Force (Black, 32GB)",
      "Category": "Mobiles",
      "Description": "",
      "Price": 34999,
      "Rating": 3,
      "Image": "Moto5.jpg"
    },
    {
      "id": 112,
      "Name": "Canon EOS 1200D 18MP Digital SLR",
      "Category": "Camera",
      "Description": "with 18-55mm and 55-250 mm IS Lens is easy to use and it delivers results that you will love for sure.",
      "Price": 26999,
      "Rating": 4.5,
      "Image": "Camera1.jpg"
    },
    {
      "id": 113,
      "Name": "Nikon D5200 24.1MP Digital SLR Camera",
      "Category": "Camera",
      "Description": "The Nikon D5200 Digital SLR Camera has 3-inch vari-angle in high resolution, LCD monitor, that helps photographers to compose their shots perfectly, even if they are planning to take it from difficult and unusual angles.",
      "Price": 27299,
      "Rating": 4.5,
      "Image": "Camera2.jpg"
    },
    {
      "id": 114,
      "Name": "Apple Macbook Pro MD101HN/A 13-inch Laptop",
      "Category": "Laptop",
      "Description": "The Apple Macbook Pro has a design that can only come from the artists at apple. The lightweight grey-white shiny finish with a simplistic apple logo is something that will turn heads wherever you go. The design uses lightweight but strong aluminium to make sure that your system weighs only 1.35kg",
      "Price": 55999,
      "Rating": 4.5,
      "Image": "Laptop1.jpg"
    },
    {
      "id": 115,
      "Name": "Dell Inspiron 3542 15.6-inch Laptop ",
      "Category": "Laptop",
      "Description": "Whether you are an entrepreneur, a photographer, a designer, a college student or a working professional, a laptop is a must-have product for you. It is a portable, compact device that will let you do almost everything that you can do on a desktop.",
      "Price": 26499,
      "Rating": 4,
      "Image": "Laptop2.jpg"
    },
    {
      "id": 116,
      "Name": "Tom & Jerry Boys' T-Shirt",
      "Category": "Clothes",
      "Description": "Round neck short sleeve, Chutki logo plestisol print, Soft feel and durable",
      "Price": 179,
      "Rating": 3,
      "Image": "Clothes1.jpg"
    },
    {
      "id": 117,
      "Name": "Batman Boys' T-Shirt",
      "Category": "Clothes",
      "Description": "100% Cotton, Solid pattern Tee, Crew neck raglan full sleeve tee",
      "Price": 375,
      "Rating": 3,
      "Image": "Clothes2.jpg"
    },
    {
      "id": 118,
      "Name": "Cherokee Girls Casual Dress",
      "Category": "Clothes",
      "Description": "100% Cotton, Half sleeve, Plain Normal machine wash",
      "Price": 299,
      "Rating": 3,
      "Image": "Clothes3.jpg"
    }];
*/