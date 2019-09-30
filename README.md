# App Outlet
App Outlet Backend is the module that provides the services needed for communication interfaces.

Requires NodeJS and MongoDB
## Settings
Basic settings are set in a enviroment variables. You can create the variables it up according to your environment. These are the enviroments variables:
* `PORT` - default value: 3001
* `OUTLET_DEV_MODE`  - default value: true
* `OUTLET_DATABASE_URL` - has no default value. Must be set
* `OUTLET_SYNC_INTERVAL` - default value: 1000 * 60
* `OUTLET_SEARCH_LIMIT` - default value: 30
* `OUTLET_SECTION_LIMIT` - default value: 10
* `OUTLET_ALLOW_SYNC`- default value: true
* `OUTLET_REDIS_URL` - connection url from Redis (cache server)
* `OUTLET_SYNC_TIMER` - delay time to run scripts to run synchronizantion with app repositories

## Running the server
Install the dependencies with `npm`:
```sh
$ npm install
```
Next, run the server  again with `npm`:
```sh
$ npm start
```

## Tests
Run tests with `npx`:
```sh
$ npx jest
```

## License
MIT License

Copyright (c) 2019 App Outlet

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
