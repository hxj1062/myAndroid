window.showErrBottom = function() {
       let container = document.createElement('div');
       container.style.width = '100%';
       container.style.height = '50px';
       container.style.backgroundColor = '#dedede';
       container.style.position = 'fixed';
       container.style.bottom = '0px';
       container.style.display = 'flex';
       container.style.justifyContent = 'center';
       container.style.alignItems = 'center';
       let upload = document.createElement('div');
       upload.innerText = '异常上报';
       upload.style.textAlign = 'center';
       upload.style.backgroundColor = '#FF7500';
       upload.style.color = '#ffffff';
       upload.style.fontSize = '18px';
       upload.style.width = '90%';
       upload.style.height = '40px';
       upload.style.lineHeight = '40px';
       upload.style.borderRadius = '4px';
       upload.onclick = function(){
           alert('测试');
       }
       container.appendChild(upload);
       let body = document.getElementsByTagName('body')[0];
       body.appendChild(container)
   }
   window.showErrBottom()
