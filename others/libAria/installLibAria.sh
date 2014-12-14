#!/bin/bash
ariaPath=/usr/local/Aria
mobileSimPath=/usr/local/MobileSim
tar xzvf ARIA-2.8.1+gcc4.6.tgz
tar xzvf MobileSim-0.7.3+gcc4.6.tgz
rm -f -R $ariaPath
rm -f -R $mobileSimPath
mv -f Aria-2.8.1 $ariaPath
mv -f MobileSim-0.7.3 $mobileSimPath
echo '/usr/local/Aria/lib' > /etc/ld.so.conf.d/aria.conf
ldconfig
sudo ln -s -f $mobileSimPath/MobileSim /usr/bin/mobilesim
echo 'Librería instalada'
