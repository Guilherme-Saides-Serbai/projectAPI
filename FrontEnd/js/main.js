/*

descrição: tudo de setar importante aqui :) . 
fuck rakin.

*/

try {
    document.addEventListener("DOMContentLoaded", () => {
        // main inicio
        console.log("deu load");

        // botão chad
        const musica8BITbotao = new Audio("../public/audio/8bitKubbi.mp3");
        let isPlaying = false;

        //tocar no comeco do html [] >>>
        // const musica8BIT = new Audio('/audio/8bitKubbi.mp3');
        // musica8BIT.play()
        // fim do tocar no comeco do html [] >>>

        let botaoMusica = document.getElementById("botaoMusica");

        if (botaoMusica) {
            document
                .getElementById("botaoMusica")
                .addEventListener("click", () => {
                    // Cria um novo objeto de áudio e define o caminho do arquivo
                    let botaozudo = document.getElementById("botaoMusica");
                    if (!isPlaying) {
                        musica8BITbotao.play();
                        isPlaying = true; // Define que o áudio está tocando
                        botaozudo.innerHTML = ` Parar musica `;
                    } else {
                        musica8BITbotao.pause(); // Pausa o áudio
                        musica8BITbotao.currentTime = 0; // Reinicia o áudio para o início
                        isPlaying = false; // Define que o áudio não está mais tocando
                        botaozudo.innerHTML = ` Botar musica `;
                    }
                });
        } else {
            console.info("Não tem botao de musica nessa url !!");
        }

        // final do main
    });
} catch (error) {
    console.info(`o coiso não carregou pois ${error}`);
}

// function ajax(){
// 			var req = new XMLHttpRequest();
// 			req.onreadystatechange = function(){
// 				if (req.readyState == 4 && req.status == 200) {
// 						document.getElementById('chat').innerHTML = req.responseText;
// 				}
// 			}
// 			req.open('GET', 'chat.php', true);
// 			req.send();
// 		}

// 		setInterval(function(){ajax();}, 2000);
