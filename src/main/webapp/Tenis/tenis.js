function visualizarTenis(caminhoImagem, nome, avaliacao, descricao) {
    const modal = document.getElementById("visualizar-modal");
    const modalImagem = document.getElementById("modal-imagem");
    const modalAvaliacao = document.getElementById("modal-avaliacao");
    const modalDescricao = document.getElementById("modal-descricao");

    // Verifique se o caminho da imagem foi passado corretamente
    if (caminhoImagem) {
        modalImagem.src = caminhoImagem.replace(/\\/g, '/');
    } else {
        modalImagem.alt = "Imagem não disponível";
        modalImagem.src = "";  // Limpe a imagem se não houver caminho
    }

    // Exibe o nome, avaliação e descrição no modal
    modalAvaliacao.textContent = `Avaliação: ${avaliacao}`;
    modalDescricao.textContent = `Descrição: ${descricao}`;

    // Exibe o modal
    modal.style.display = "block";
}

function fecharModal() {
    const modal = document.getElementById("visualizar-modal");
    modal.style.display = "none"; // Fecha o modal
}
