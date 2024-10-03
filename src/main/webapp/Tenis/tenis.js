function visualizarTenis(caminhoImagem) {
    const modal = document.getElementById("visualizar-modal");
    const modalImagem = document.getElementById("modal-imagem");

    // Verifique se o caminho da imagem foi passado corretamente
    if (caminhoImagem) {
        modalImagem.src = caminhoImagem;
    } else {
        modalImagem.alt = "Imagem não disponível";
        modalImagem.src = "";  // Limpe a imagem se não houver caminho
    }

    modal.style.display = "block"; // Exibe o modal
}

function fecharModal() {
    const modal = document.getElementById("visualizar-modal");
    modal.style.display = "none"; // Fecha o modal
}